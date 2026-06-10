<script setup>
import { ref } from 'vue'
import ExplainTab from './components/ExplainTab.vue'
import DiagnoseTab from './components/DiagnoseTab.vue'
import PracticeTab from './components/PracticeTab.vue'

const tabs = [
  { id: 'explain', label: '代码解释', icon: '📖' },
  { id: 'diagnose', label: '错误诊断', icon: '🔧' },
  { id: 'practice', label: '练习题', icon: '✏️' }
]

const activeTab = ref('explain')
</script>

<template>
  <div class="app-shell">
    <header class="hero">
      <div class="hero-inner">
        <p class="eyebrow">智谱 AI 驱动 · 编程初学者助手</p>
        <h1>智能编程辅导系统</h1>
        <p class="subtitle">
          代码解释、错误诊断与个性化练习，一站式提升编程能力
        </p>
      </div>
    </header>

    <main class="container">
      <nav class="tab-bar">
        <button
          v-for="tab in tabs"
          :key="tab.id"
          class="tab-btn"
          :class="{ active: activeTab === tab.id }"
          @click="activeTab = tab.id"
        >
          <span class="tab-icon">{{ tab.icon }}</span>
          {{ tab.label }}
        </button>
      </nav>

      <section class="panel">
        <ExplainTab v-if="activeTab === 'explain'" />
        <DiagnoseTab v-else-if="activeTab === 'diagnose'" />
        <PracticeTab v-else />
      </section>
    </main>

    <footer class="footer">
      后端 Spring Boot · 前端 Vue 3 · 大模型 智谱 GLM
    </footer>
  </div>
</template>
