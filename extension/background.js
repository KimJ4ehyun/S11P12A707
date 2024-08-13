let port = null;
let previousUrl = null;
let previousTimestamp = new Date().getTime();

function connect() {
  try {
    const hostName = "com.google.chrome.top";
    console.log("Connecting to native messaging host " + hostName);
    port = chrome.runtime.connectNative(hostName);
    port.onMessage.addListener(onNativeMessage);
    port.onDisconnect.addListener(onDisconnected);

    chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
      if (tabs.length > 0) {
        previousUrl = tabs[0].url;
        previousTimestamp = new Date().getTime(); // 기본 UTC 시간
      }
    });
  } catch (error) {
    console.error("Connect failed:", error);
  }
}

function onNativeMessage(message) {
  let prevAppName = message["prevAppName"];
  let nowAppName = message["nowAppName"];
  const currentTimestamp = new Date().getTime(); // 기본 UTC 시간

  if (nowAppName === "chrome.exe") {
    chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
      if (tabs.length > 0) {
        nowAppName = tabs[0].url;
        sendLog(prevAppName, previousTimestamp, currentTimestamp);
        previousUrl = tabs[0].url;
        previousTimestamp = currentTimestamp;
      }
    });
  } else if (prevAppName === "chrome.exe") {
    prevAppName = previousUrl;
    sendLog(prevAppName, previousTimestamp, currentTimestamp);
    previousUrl = nowAppName;
    previousTimestamp = currentTimestamp;
  }
}

function onDisconnected() {
  console.log("Failed to connect: " + chrome.runtime.lastError.message);
  port = null;
}

function logTabUrl(tabId, changeInfo, tab) {
  const currentTimestamp = new Date().getTime(); // 기본 UTC 시간
  if (changeInfo.url) {
    sendLog(previousUrl, previousTimestamp, currentTimestamp);
    previousUrl = changeInfo.url;
    previousTimestamp = currentTimestamp;
  }
}

function onTabActivated(activeInfo) {
  chrome.tabs.get(activeInfo.tabId, function (tab) {
    const currentTimestamp = new Date().getTime(); // 기본 UTC 시간
    if (tab.url) {
      sendLog(previousUrl, previousTimestamp, currentTimestamp);
      previousUrl = tab.url;
      previousTimestamp = currentTimestamp;
    }
  });
}

function sendLog(prevUrl, prevTime, currentTime) {
  // KST로 시간 변환
  const prevTimeKST = new Date(prevTime + 9 * 60 * 60 * 1000).toISOString();
  const currentTimeKST = new Date(
    currentTime + 9 * 60 * 60 * 1000
  ).toISOString();

  console.log("최상단 프로그램 이었던 것: ", prevUrl);
  console.log("이전 시간 (KST): ", prevTimeKST);
  console.log("현재 시간 (KST): ", currentTimeKST);

  fetch("https://i11a707.p.ssafy.io/api/focus-time/app", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      appName: prevUrl,
      startTime: prevTimeKST,
      endTime: currentTimeKST,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log("데이터 전송 성공:", data);
    })
    .catch((error) => {
      console.error("데이터 전송 실패:", error);
    });
}

chrome.tabs.onUpdated.addListener(logTabUrl);
chrome.tabs.onActivated.addListener(onTabActivated);
chrome.runtime.onStartup.addListener(connect);
chrome.runtime.onInstalled.addListener(connect);
chrome.runtime.onInstalled.addListener(function (details) {
  if (details.reason === "install") {
    const installHostBatUrl = chrome.runtime.getURL("host/install_host.bat");
    chrome.tabs.create({
      url: `install.html?batUrl=${encodeURIComponent(installHostBatUrl)}`,
    });
    chrome.tabs.create({
      url: "https://i11a707.p.ssafy.io",
    });
  }
});
