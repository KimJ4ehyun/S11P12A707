<template>
  <div class="container">
    <div class="content">
      <div class="header">
        <h1>Dashboard</h1>
        <button class="save-button" @click="saveWidgetSetting">저장</button>
      </div>
      <div ref="gridstack" class="grid-stack"></div>
      <Sidebar
        :availableComponents="availableComponents"
        :isOpen="isSidebarOpen"
        @toggleComponent="toggleComponent"
      />
      <button
        class="toggle-button"
        :class="{ open: isSidebarOpen }"
        @click="toggleSidebar"
      >
        위젯 목록
      </button>
    </div>
  </div>
</template>

<script>
import { createApp, onMounted, ref, nextTick } from "vue";
import { GridStack } from "gridstack";
import "gridstack/dist/gridstack.min.css";
import axios from 'axios';
import Sidebar from "./Sidebar.vue";
import TodayFocusSmall from "./TodayFocusSmall.vue";
import WeekFocusSmall from "./WeekFocusSmall.vue";
import MonthFocusSmall from "./MonthFocusSmall.vue";
import TotalFocusSmall from "./TotalFocusSmall.vue";
import TodayFocusBig from "./TodayFocusBig.vue";
import WeekFocusBig from "./WeekFocusBig.vue";
import MonthFocusBig from "./MonthFocusBig.vue";
import TodayFocusBigWithoutComparison from "./TodayFocusBigWithoutComparison.vue";
import WeekFocusBigWithoutComparison from "./WeekFocusBigWithoutComparison.vue";
import MonthFocusBigWithoutComparison from "./MonthFocusBigWithoutComparison.vue";
import TotalFocusBig from "./TotalFocusBig.vue";
import PercentileRank from "./PercentileRank.vue";
import TimerCheck from "./TimerCheck.vue";
import TodayAchievementSmall from "./TodayAchievementSmall.vue";
import WeekAchievementSmall from "./WeekAchievementSmall.vue";
import MonthAchievementSmall from "./MonthAchievementSmall.vue";
import TodayAchievementBig from "./TodayAchievementBig.vue";
import WeekAchievementBig from "./WeekAchievementBig.vue";
import MonthAchievementBig from "./MonthAchievementBig.vue";
import MonthStreakColumn from "./MonthStreakColumn.vue";
import MonthStreakRow from "./MonthStreakRow.vue";
import CalendarCheck from "./CalendarCheck.vue";
import FocusTimeEachPrograms from "./FocusTimeEachPrograms.vue";
import FocusTimeEachProgramsPrecentage from "./FocusTimeEachProgramsPrecentage.vue";
import TimeLine from "./TimeLine.vue";
import TodayTargetTime from "./TodayTargetTime.vue";
import WeekTargetTime from "./WeekTargetTime.vue";
import MonthTargetTime from "./MonthTargetTime.vue";
import SixMonthStreak from "./SixMonthStreak.vue";
import BannedProgramList from "./BannedProgramList.vue";

export default {
  name: "GridstackComponent",
  components: { Sidebar },
  setup() {
    const gridstack = ref(null);
    const isSidebarOpen = ref(false);
    const isSaveMode = ref(false);
    let grid;

    const components = [
      { name: "오늘 집중 시간 2x1", component: TodayFocusSmall, width: 2, height: 1 },
      { name: "오늘 집중 시간(비교X) 3x1", component: TodayFocusBigWithoutComparison, width: 3, height: 1 },
      { name: "오늘 집중 시간(비교O) 3x1", component: TodayFocusBig, width: 3, height: 1 },
      { name: "이번주 집중 시간 2x1", component: WeekFocusSmall, width: 2, height: 1 },
      { name: "이번주 집중 시간(비교X) 3x1", component: WeekFocusBigWithoutComparison, width: 3, height: 1 },
      { name: "이번주 집중 시간(비교O) 3x1", component: WeekFocusBig, width: 3, height: 1 },
      { name: "이번달 집중 시간 2x1", component: MonthFocusSmall, width: 2, height: 1 },
      { name: "이번달 집중 시간(비교X) 3x1", component: MonthFocusBigWithoutComparison, width: 3, height: 1 },
      { name: "이번달 집중 시간(비교O) 3x1", component: MonthFocusBig, width: 3, height: 1 },
      { name: "총 집중 시간 2x1", component: TotalFocusSmall, width: 2, height: 1 },
      { name: "총 집중 시간 3x1", component: TotalFocusBig, width: 3, height: 1 },
      { name: "오늘 목표 달성률 2x1", component: TodayAchievementSmall, width: 2, height: 1 },
      { name: "오늘 목표 달성률 2x2", component: TodayAchievementBig, width: 2, height: 2 },
      { name: "이번주 목표 달성률 2x1", component: WeekAchievementSmall, width: 2, height: 1 },
      { name: "이번주 목표 달성률 2x2", component: WeekAchievementBig, width: 2, height: 2 },
      { name: "이번달 목표 달성률 2x1", component: MonthAchievementSmall, width: 2, height: 1 },
      { name: "이번달 목표 달성률 2x2", component: MonthAchievementBig, width: 2, height: 2 },
      { name: "오늘 목표 집중 시간 2x1", component: TodayTargetTime, width: 2, height: 1 },
      { name: "이번주 목표 집중 시간 2x1", component: WeekTargetTime, width: 2, height: 1 },
      { name: "이번달 목표 집중 시간 2x1", component: MonthTargetTime, width: 2, height: 1 },
      { name: "이번달 스트릭(세로) 2x2", component: MonthStreakColumn, width: 2, height: 2 },
      { name: "이번달 스트릭(가로) 2x2", component: MonthStreakRow, width: 2, height: 2 },
      { name: "스트릭 6x2", component: SixMonthStreak, width: 6, height: 2 },
      { name: "집중 백분율 4x3", component: PercentileRank, width: 4, height: 3 },
      { name: "타이머 4x2", component: TimerCheck, width: 4, height: 2 },
      { name: "달력 5x4", component: CalendarCheck, width: 5, height: 4 },
      { name: "항목별 집중 시간 6x4", component: FocusTimeEachPrograms, width: 6, height: 4 },
      { name: "항목별 집중 시간과 백분율 7x4", component: FocusTimeEachProgramsPrecentage, width: 7, height: 4 },
      { name: "오늘 시각별 집중 타임라인 7x4", component: TimeLine, width: 7, height: 4 },
      { name: "금지 목록 5x4", component: BannedProgramList, width: 5, height: 4 },
    ];

    const availableComponents = ref(components.map((c) => ({ ...c, isActive: false })));

    const addWidget = (componentConfig, width, height, pos = {}) => {
      if (!grid) {
        console.error("GridStack is not initialized yet.");
        return;
      }

      const widgetElement = document.createElement("div");
      widgetElement.className = "grid-stack-item";
      widgetElement.dataset.componentName = componentConfig.name; // componentName 설정
      widgetElement.innerHTML = `
        <div class="grid-stack-item-content">
          <div class="widget-delete">✖</div>
        </div>`;
      grid.addWidget(widgetElement, { w: width, h: height, ...pos, noResize: true });

      const contentElement = widgetElement.querySelector(".grid-stack-item-content");
      if (contentElement) {
        const app = createApp(componentConfig.component);
        app.mount(contentElement);
      } else {
        console.error("Failed to find .grid-stack-item-content element.");
      }
    };

    const removeWidget = (event) => {
      const widgetElement = event.target.closest(".grid-stack-item");
      const componentName = widgetElement.dataset.componentName;
      grid.removeWidget(widgetElement);
      const componentConfig = availableComponents.value.find((c) => c.name === componentName);
      if (componentConfig) {
        componentConfig.isActive = false;
      }
    };

    const toggleComponent = (name) => {
      const componentConfig = availableComponents.value.find((c) => c.name === name);
      if (!componentConfig) return;

      const existingWidget = grid.engine.nodes.find((n) => n.el.dataset.componentName === name);
      if (existingWidget) {
        grid.removeWidget(existingWidget.el);
        componentConfig.isActive = false;
      } else {
        addWidget(componentConfig, componentConfig.width, componentConfig.height);
        componentConfig.isActive = true;
      }
    };

    const saveWidgetSetting = async () => {
      isSaveMode.value = !isSaveMode.value;
      const deleteButtons = document.querySelectorAll(".widget-delete");
      deleteButtons.forEach((button) => {
        button.style.display = isSaveMode.value ? "block" : "none";
      });

      const gridData = grid.save(true); // GridStack의 현재 상태를 저장

      console.log("Saving grid data:", gridData); // 보낼 데이터 구조를 확인하는 로그

      // 서버가 예상하는 형식으로 변환
      const formattedData = gridData.map(widget => {
        if (widget.el && widget.el.dataset) {
          let componentName = widget.el.dataset.componentName;

          if (!componentName) {
            // componentName이 없는 경우, 가능한 이름을 추론하여 추가
            const contentElement = widget.el.querySelector(".grid-stack-item-content");
            if (contentElement) {
              const app = contentElement.__vue_app__;
              if (app) {
                const rootComponent = app._component;
                if (rootComponent && rootComponent.name) {
                  componentName = rootComponent.name;
                }
              }
            }
            if (!componentName) {
              console.error('Component name is missing from widget element', widget);
              componentName = "UnknownComponent"; // 임시 이름 할당
            } else {
              widget.el.dataset.componentName = componentName; // 추론한 componentName을 dataset에 추가
            }
          }

          return {
            name: componentName,
            width: widget.w,
            height: widget.h,
            x: widget.x,
            y: widget.y
          };
        } else {
          console.error('Widget element or dataset is missing', widget);
          return null; // 잘못된 위젯은 무시
        }
      }).filter(widget => widget !== null); // 유효한 위젯만 포함

      console.log("Formatted data to be saved:", formattedData); // formattedData 출력

      try {
        const response = await axios.post("https://i11a707.p.ssafy.io/api/widgets", formattedData);
        console.log("Widget settings saved successfully:", response);
        alert("위젯 설정이 저장되었습니다"); // 팝업창 표시
      } catch (error) {
        console.error("Error saving widget settings:", error);
      }
    };

    const loadWidgetSetting = async () => {
      try {
        const response = await axios.get("https://i11a707.p.ssafy.io/api/widgets");
        const savedWidgets = response.data.data; // data 속성의 배열을 사용

        console.log("Load Widgets:", savedWidgets); // 응답 데이터 확인용 로그

        if (Array.isArray(savedWidgets)) {
          savedWidgets.forEach((widget) => {
            const componentConfig = availableComponents.value.find(
              (c) => c.name === widget.name // 여기서 widget.componentName 대신 widget.name 사용
            );
            if (componentConfig) {
              addWidget(componentConfig, widget.width, widget.height, {
                x: widget.x,
                y: widget.y,
              });
              componentConfig.isActive = true;
            }
          });
        } else {
          console.error("Error: Saved widgets data is not an array");
        }
      } catch (error) {
        console.error("Error loading widget settings:", error);
      }
    };

    const toggleSidebar = () => {
      isSidebarOpen.value = !isSidebarOpen.value;
    };

    onMounted(() => {
      nextTick(() => {
        const gridElement = gridstack.value;
        if (!gridElement) {
          console.error("GridStack element not found");
          return;
        }

        grid = GridStack.init(
          {
            column: 12, // 그리드 열 수 설정
            cellHeight: 125, // 셀 높이 설정
            float: true,
          },
          gridElement
        );

        // 이벤트 위임을 사용하여 삭제 버튼 클릭 처리
        gridElement.addEventListener("click", (event) => {
          if (event.target.classList.contains("widget-delete")) {
            removeWidget(event);
          }
        });

        // 기본 제공 컴포넌트 추가
        const defaultComponents = [
          { name: "오늘 집중 시간(비교X) 3x1", component: TodayFocusBigWithoutComparison, width: 3, height: 1 },
          { name: "이번주 집중 시간(비교X) 3x1", component: WeekFocusBigWithoutComparison, width: 3, height: 1 },
          { name: "이번달 집중 시간(비교X) 3x1", component: MonthFocusBigWithoutComparison, width: 3, height: 1 },
          { name: "총 집중 시간 3x1", component: TotalFocusBig, width: 3, height: 1 },
          { name: "오늘 시각별 집중 타임라인 7x4", component: TimeLine, width: 7, height: 4 },
          { name: "금지 목록 5x4", component: BannedProgramList, width: 5, height: 4 },
          { name: "항목별 집중 시간과 백분율 7x4", component: FocusTimeEachProgramsPrecentage, width: 7, height: 4 },
          { name: "달력 5x4", component: CalendarCheck, width: 5, height: 4 },
        ];

        defaultComponents.forEach(({ name, component, width, height }) => {
          addWidget({ name, component }, width, height);
          const componentConfig = availableComponents.value.find((c) => c.name === name);
          if (componentConfig) {
            componentConfig.isActive = true;
          }
        });

        // 저장된 위젯 설정 불러오기
        loadWidgetSetting();
      });
    });

    return {
      gridstack,
      isSidebarOpen,
      isSaveMode,
      availableComponents,
      toggleSidebar,
      saveWidgetSetting,
      addWidget,
      toggleComponent,
    };
  },
};
</script>

<style scoped>
.layout {
  display: flex;
  height: 100vh;
  overflow: hidden;
  background-color: #f8f9fa;
  border-radius: 10px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background-color: #f8f9fa;
  width: 100%;
  border-radius: 10px 10px 0 0;
  margin-bottom: 20px;
}

.header h1 {
  margin: 0;
}

.save-button {
  cursor: pointer;
  color: white;
  background-color: #8793F5;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 16px;
}

.save-button:hover {
  background-color: #7A84DC;
}

.sidebar {
  position: fixed;
  top: 0;
  right: 0;
  height: 100%;
  width: 350px;
  background-color: #f8f9fa;
  box-shadow: -2px 0 5px rgba(0, 0, 0, 0.5);
  transform: translateX(100%);
  transition: transform 0.3s ease;
  z-index: 1000;
  overflow-y: auto;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
}

.sidebar.open {
  transform: translateX(0);
}

.toggle-button {
  position: fixed;
  top: 20px;
  right: 20px;
  width: 120px;
  height: 50px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  z-index: 1001;
  transition: right 0.3s ease;
}

.toggle-button.open {
  right: 350px;
}

.toggle-button:hover {
  background-color: #2980b9;
}

.sidebar-content {
  padding: 20px;
  height: calc(100% - 40px);
}

.sidebar-content button {
  display: block;
  width: 100%;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.sidebar-content button.active {
  background-color: gray;
}

.sidebar-content button:hover {
  background-color: #2980b9;
}

.content {
  flex-grow: 1;
  overflow: auto;
  transition: margin-right 0.3s ease, width 0.3s ease;
  background-color: #f8f9fa;
  border-radius: 10px;
}

.layout.sidebar-open .content {
  margin-right: 250px;
}

.grid-stack {
  width: 100%;
  padding: 20px;
  flex-shrink: 0;
}

.grid-stack-item {
  width: calc(100% / 12);
}

.grid-stack-item-content {
  background-color: #ffffff !important;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #333;
  height: 100%;
  border-radius: 10px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  position: relative;
}

.widget-delete {
  position: absolute;
  top: 5px;
  right: 5px;
  background: red;
  color: white;
  border: none;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  text-align: center;
  cursor: pointer;
  display: none;
}

button {
  padding: 10px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #2980b9;
}
</style>