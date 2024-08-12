var APPLICATION_SERVER_URL = "https://i11a707.p.ssafy.io/ov-server/";
var LIVEKIT_URL = "wss://i11a707.p.ssafy.io:4443";


const LivekitClient = window.LivekitClient;
var room;

// 각종 이벤트 리스너
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("dashboard-link").addEventListener("click", godashboard);
    document.getElementById("join-button").addEventListener("click", joinRoom);
    document.getElementById("leave-room-button").addEventListener("click", leaveRoom);
    
    generateFormValues();
});

function godashboard(event) {
    event.preventDefault(); // 기본 링크 동작을 방지합니다.
    const href = this.getAttribute('href'); // 'href' 속성을 가져옵니다.
    chrome.tabs.create({ url: href }); // 새 탭에서 링크를 엽니다.
}

function configureUrls() {}

async function joinRoom() {
  // Disable 'Join' button
  document.getElementById("join-button").disabled = true;
  document.getElementById("join-button").innerText = "Joining...";

  // Initialize a new Room object
  room = new LivekitClient.Room();

  // Specify the actions when events take place in the room
  // On every new Track received...
  room.on(
    LivekitClient.RoomEvent.TrackSubscribed,
    (track, _publication, participant) => {
      addTrack(track, participant.identity);
    }
  );

  // On every new Track destroyed...
  room.on(
    LivekitClient.RoomEvent.TrackUnsubscribed,
    (track, _publication, participant) => {
      track.detach();
      document.getElementById(track.sid)?.remove();

      if (track.kind === "video") {
        removeVideoContainer(participant.identity);
      }
    }
  );

    try {
        // Get the room name and participant name from the form
        const roomName = document.getElementById("room-name").value;
        const userName = document.getElementById("participant-name").value;
        // Get a token from your application server with the room name and participant name
        const token = await getToken(roomName, userName);
        // Connect to the room with the LiveKit URL and the token
        await room.connect(LIVEKIT_URL, token);
        // Hide the 'Join room' page and show the 'Room' page
        document.getElementById("room-title").innerText = roomName;
        document.getElementById("join").hidden = true;
        document.getElementById("room").hidden = false;
        // Publish your camera and microphone
        await room.localParticipant.enableCameraAndMicrophone();
        const localVideoTrack = room.localParticipant.videoTrackPublications.values().next().value.track;

        addTrack(localVideoTrack, userName, true);
    } catch (error) {
        console.log("There was an error connecting to the room:", error.message);
        await leaveRoom();
    }
  try {
    // Get the room name and participant name from the form
    const roomName = document.getElementById("room-name").value;
    const userName = document.getElementById("participant-name").value;

    // Get a token from your application server with the room name and participant name
    const token = await getToken(roomName, userName);

    // Connect to the room with the LiveKit URL and the token
    await room.connect(LIVEKIT_URL, token);

    // Hide the 'Join room' page and show the 'Room' page
    document.getElementById("room-title").innerText = roomName;
    document.getElementById("join").hidden = true;
    document.getElementById("room").hidden = false;

    // Publish your camera and microphone
    await room.localParticipant.enableCameraAndMicrophone();
    const localVideoTrack = room.localParticipant.videoTrackPublications
      .values()
      .next().value.track;
    addTrack(localVideoTrack, userName, true);
  } catch (error) {
    console.log("There was an error connecting to the room:", error.message);
    await leaveRoom();
  }
}

function addTrack(track, participantIdentity, local = false) {
  const element = track.attach();
  element.id = track.sid;

  /* If the track is a video track, we create a container and append the video element to it 
    with the participant's identity */
  if (track.kind === "video") {
    const videoContainer = createVideoContainer(participantIdentity, local);
    videoContainer.append(element);
    appendParticipantData(
      videoContainer,
      participantIdentity + (local ? " (You)" : "")
    );
  } else {
    document.getElementById("layout-container").append(element);
  }
}

async function leaveRoom() {
  // Leave the room by calling 'disconnect' method over the Room object
  await room.disconnect();

  // Remove all HTML elements inside the layout container
  removeAllLayoutElements();

  // Back to 'Join room' page
  document.getElementById("join").hidden = false;
  document.getElementById("room").hidden = true;

  // Enable 'Join' button
  document.getElementById("join-button").disabled = false;
  document.getElementById("join-button").innerText = "Join!";
}

window.onbeforeunload = () => {
  room?.disconnect();
};

window.onload = generateFormValues;

function generateFormValues() {
    const participantName = getUserInfo();
    console.log(participantName);
    document.getElementById("room-name").value = "Test Room";
    document.getElementById("participant-name").value = participantName.value;
}

function createVideoContainer(participantIdentity, local = false) {
  const videoContainer = document.createElement("div");
  videoContainer.id = `camera-${participantIdentity}`;
  videoContainer.className = "video-container";
  const layoutContainer = document.getElementById("layout-container");

  if (local) {
    layoutContainer.prepend(videoContainer);
  } else {
    layoutContainer.append(videoContainer);
  }

  return videoContainer;
}

function appendParticipantData(videoContainer, participantIdentity) {
  const dataElement = document.createElement("div");
  dataElement.className = "participant-data";
  dataElement.innerHTML = `<p>${participantIdentity}</p>`;
  videoContainer.prepend(dataElement);
}

function removeVideoContainer(participantIdentity) {
  const videoContainer = document.getElementById(
    `camera-${participantIdentity}`
  );
  videoContainer?.remove();
}

function removeAllLayoutElements() {
  const layoutElements = document.getElementById("layout-container").children;
  Array.from(layoutElements).forEach((element) => {
    element.remove();
  });
}

async function getToken(roomName, participantName) {
  const response = await fetch(APPLICATION_SERVER_URL + "token", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      roomName,
      participantName,
    }),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(`Failed to get token: ${error.errorMessage}`);
  }

  const token = await response.json();
  return token.token;
}

// 내 정보 불러오기
var DEFAULT_URL = "https://i11a707.p.ssafy.io/api";

async function getUserInfo() {
    try {
        const response = await fetch(DEFAULT_URL + "/user", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        // .then(
        const data = await response.json(); // 응답 본문을 JSON으로 파싱
        const participantName = data.data.nickname;
        console.log(participantName);
        return participantName; // 파싱된 데이터를 반환
        // )
    } catch (error) {
        console.log('getUserInfo 안됨: ', error); // 오류를 콘솔에 출력
        throw error; // 필요하다면 오류를 다시 던져서 호출자에게 알림
    }
}