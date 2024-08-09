document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const batUrl =
    urlParams.get("batUrl") ||
    chrome.runtime.getURL("host/initial_setting.bat");
  const downloadLink = document.getElementById("downloadLink");
  downloadLink.href = batUrl;
  downloadLink.download = "initial_setting.bat";

  const copyCode = document.getElementById("copyCode");
  const copyStatus = document.getElementById("copyStatus");
  const profilePathInput = document.getElementById("profilePathInput");
  const generatePathButton = document.getElementById("generatePathButton");
  const finalPath = document.getElementById("finalPath");

  const extensionId = "YOUR_EXTENSION_ID"; // 확장 프로그램 ID를 적절히 변경
  const extensionVersion = "YOUR_EXTENSION_VERSION"; // 확장 프로그램 버전을 적절히 변경

  copyCode.addEventListener("click", function () {
    const textToCopy = "chrome://version/";
    navigator.clipboard
      .writeText(textToCopy)
      .then(function () {
        copyStatus.innerText = "URL이 클립보드에 복사되었습니다.";
        setTimeout(() => (copyStatus.innerText = ""), 2000); // 2초 후에 메시지 지우기
      })
      .catch(function (error) {
        copyStatus.innerText = "URL 복사에 실패했습니다.";
        console.error("복사 실패:", error);
      });
  });

  generatePathButton.addEventListener("click", function () {
    const profilePath = profilePathInput.value.trim();
    if (profilePath) {
      const fullPath = `${profilePath}\\Extensions\\${extensionId}\\${extensionVersion}\\host`;
      finalPath.innerText = `우측 경로를 클릭하여 복사해주세요. ${fullPath}`;
      finalPath.style.cursor = "pointer";
    } else {
      finalPath.innerText = "프로필 경로를 입력해주세요.";
    }
  });

  finalPath.addEventListener("click", function () {
    const textToCopy = finalPath.innerText
      .replace("확장 프로그램 경로: ", "")
      .trim();
    navigator.clipboard
      .writeText(textToCopy)
      .then(function () {
        alert("경로가 클립보드에 복사되었습니다.");
      })
      .catch(function (error) {
        alert("경로 복사에 실패했습니다.");
        console.error("복사 실패:", error);
      });
  });
});