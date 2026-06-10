<script setup>
import { ref, computed } from 'vue'
import ExplainTab from './components/ExplainTab.vue'
import DiagnoseTab from './components/DiagnoseTab.vue'
import PracticeTab from './components/PracticeTab.vue'

const tabs = [
  {
    id: 'explain',
    label: '代码解释',
    desc: '逐段读懂代码',
    icon: '📖'
  },
  {
    id: 'diagnose',
    label: '错误诊断',
    desc: '定位并修复 Bug',
    icon: '🔧'
  },
  {
    id: 'practice',
    label: '练习题',
    desc: '定制刷题训练',
    icon: '✏️'
  }
]

const activeTab = ref('explain')

const activeMeta = computed(() => tabs.find(t => t.id === activeTab.value))
</script>

<template>
  <div class="app-shell">
    <div class="bg-glow bg-glow-a" />
    <div class="bg-glow bg-glow-b" />

    <header class="hero">
      <div class="hero-inner">
        <div class="hero-badge">
          <span class="pulse-dot" />
          智谱 GLM · 在线辅导
        </div>
        <h1>智能编程辅导系统</h1>
        <p class="subtitle">
          面向编程初学者的 AI 学习助手，支持代码解释、错误诊断与个性化练习生成
        </p>
        <div class="feature-chips">
          <span class="chip">Spring Boot 后端</span>
          <span class="chip">Vue 3 前端</span>
          <span class="chip">OpenAI 兼容 API</span>
        </div>
      </div>
    </header>

    <main class="container">
      <nav class="tab-bar" role="tablist">
        <button
          v-for="tab in tabs"
          :key="tab.id"
          role="tab"
          class="tab-btn"
          :class="{ active: activeTab === tab.id }"
          :aria-selected="activeTab === tab.id"
          @click="activeTab = tab.id"
        >
          <span class="tab-icon">{{ tab.icon }}</span>
          <span class="tab-text">
            <span class="tab-label">{{ tab.label }}</span>
            <span class="tab-desc">{{ tab.desc }}</span>
          </span>
        </button>
      </nav>

      <section class="panel">
        <div class="panel-header">
          <div>
            <h2>{{ activeMeta?.label }}</h2>
            <p>{{ activeMeta?.desc }}</p>
          </div>
        </div>

        <ExplainTab v-if="activeTab === 'explain'" />
        <DiagnoseTab v-else-if="activeTab === 'diagnose'" />
        <PracticeTab v-else />
      </section>
    </main>

    <footer class="footer">
      <span>智能编程辅导系统</span>
      <span class="footer-divider">·</span>
      <span>Powered by 智谱 AI</span>
    </footer>
  </div>
</template>
