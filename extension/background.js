let port = null;
let previousUrl = null;

function connect() {
  const hostName = "com.google.chrome.top";
  console.log("Connecting to native messaging host" + hostName);
  port = chrome.runtime.connectNative(hostName);
  port.onMessage.addListener(onNativeMessage);
  port.onDisconnect.addListener(onDisconnected);

  // 현재 활성 탭의 URL을 previousUrl에 저장
  chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
    if (tabs.length > 0) {
      previousUrl = tabs[0].url;
    }
  });
}

function onNativeMessage(message) {
  let prevAppName = message["prevAppName"];
  let nowAppName = message["nowAppName"];
  if (nowAppName === "chrome.exe") {
    chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
      if (tabs.length > 0) {
        nowAppName = tabs[0].url;
      }
    });
  } else if (prevAppName === "chrome.exe") {
    prevAppName = previousUrl;
  }
  sendLog(prevAppName, nowAppName);
}

function onDisconnected() {
  console.log("Failed to connect: " + chrome.runtime.lastError.message);
  port = null;
}

function logTabUrl(tabId, changeInfo, tab) {
  if (changeInfo.url) {
    sendLog(previousUrl, changeInfo.url);
    previousUrl = changeInfo.url;
  }
}

function onTabActivated(activeInfo) {
  chrome.tabs.get(activeInfo.tabId, function (tab) {
    if (tab.url) {
      sendLog(previousUrl, tab.url);
      previousUrl = tab.url;
    }
  });
}

function sendLog(prevUrl, currentUrl) {
  console.log("이전 최상단 프로그램: ", prevUrl);
  console.log("현재 최상단 프로그램: ", currentUrl);

  fetch("https://i11a707.p.ssafy.io/api/focus-time/app", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      prevAppName: prevUrl,
      nowAppName: currentUrl,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log("Data sent successfully:", data);
    })
    .catch((error) => {
      console.error("Error sending data:", error);
    });
}

async function initialize() {
  try {
    connect();
  } catch (error) {
    console.error("Initialization failed:", error);
  }
}

chrome.tabs.onUpdated.addListener(logTabUrl);
chrome.tabs.onActivated.addListener(onTabActivated);
chrome.runtime.onStartup.addListener(initialize);
chrome.runtime.onInstalled.addListener(initialize);
chrome.runtime.onInstalled.addListener(function (details) {
  if (details.reason === "install") {
    const installHostBatUrl = chrome.runtime.getURL("host/install_host.bat");
    chrome.tabs.create({
      url: `install.html?batUrl=${encodeURIComponent(installHostBatUrl)}`,
    });
  }
});