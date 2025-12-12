<template>
  <div class="page">
    <section class="table-card">
      <!-- Top toolbar: title + summary -->
      <div class="table-toolbar">
        <div class="toolbar-left">
          <div>
            <h1 class="page-title">
              Course Progress
              <span v-if="progress.courseTitle" class="course-name">
                · {{ progress.courseTitle }}
              </span>
            </h1>
            <p class="page-subtitle">
              Track your lesson completion and overall progress for this course.
            </p>
          </div>
        </div>
        <div class="toolbar-right">
          <span v-if="progress.loading" class="status-pill loading-pill">
            <i class="pi pi-spin pi-spinner mr-2" />
            Syncing progress...
          </span>
          <span v-else class="status-pill">
            <i class="pi pi-check-circle mr-2" />
            All changes synced
          </span>
        </div>
      </div>

      <!-- Progress overview -->
      <div class="progress-overview">
        <div class="progress-main">
          <div class="progress-label-row">
            <span class="progress-label">Overall progress</span>
            <span class="progress-value">
              {{ progress.progressPercentage.toFixed(0) }}%
            </span>
          </div>
          <ProgressBar
            :value="progress.progressPercentage"
            class="progress-bar"
          >
            <span class="progress-bar-text">
              {{ progress.completedLessons }}/{{ progress.totalLessons }} lessons completed
            </span>
          </ProgressBar>

          <div class="progress-stats">
            <div class="stat-chip">
              <span class="stat-label">Total lessons</span>
              <span class="stat-value">{{ progress.totalLessons }}</span>
            </div>
            <div class="stat-chip">
              <span class="stat-label">Completed</span>
              <span class="stat-value success">
                {{ progress.completedLessons }}
              </span>
            </div>
            <div class="stat-chip">
              <span class="stat-label">Remaining</span>
              <span class="stat-value warn">
                {{ remainingLessons }}
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- Lessons list -->
      <div class="lessons-header">
        <h2 class="lessons-title">Lessons</h2>
        <span class="lessons-subtitle">
          Select lessons to mark them as completed.
        </span>
      </div>

      <DataTable
        :value="lessons"
        dataKey="id"
        class="lessons-table"
        responsiveLayout="scroll"
        :loading="progress.loading || progress.completingLesson"
      >
        <template #empty>
          <div class="empty-state">
            <i class="pi pi-folder-open empty-icon"></i>
            <p>No lessons available for this course.</p>
          </div>
        </template>

        <template #loading>
          <div class="loading-state">
            <i class="pi pi-spin pi-spinner loading-icon"></i>
            <p>Updating progress...</p>
          </div>
        </template>

        <Column header="Action" style="width: 3rem">
          <template #body="{ data }">
            <Checkbox
              :binary="true"
              :modelValue="isLessonCompleted(data.id)"
              :disabled="isLessonCompleted(data.id)"
              @change="onLessonToggle(data.id,data)"
            />
          </template>
        </Column>

        <Column field="title" header="Lesson">
          <template #body="{ data }">
            <div class="lesson-info">
              <span
                class="lesson-title"
                :class="{ completed: isLessonCompleted(data.id) }"
              >
                {{ data.title }}
              </span>
              <span class="lesson-meta">
                Lesson ID: {{ data.id }}
              </span>
            </div>
          </template>
        </Column>

        <Column header="Status" style="width: 9rem">
          <template #body="{ data }">
            <span
              :class="[
                'status-chip',
                isLessonCompleted(data.id) ? 'status-chip-completed' : 'status-chip-pending'
              ]"
            >
              <i
                :class="[
                  'chip-icon',
                  isLessonCompleted(data.id) ? 'pi pi-check-circle' : 'pi pi-circle-off'
                ]"
              />
              {{ isLessonCompleted(data.id) ? 'Completed' : 'Pending' }}
            </span>
          </template>
        </Column>
      </DataTable>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useToast } from 'primevue/usetoast'
import { useProgressStore } from '@/stores/progressStore.js'

import ProgressBar from 'primevue/progressbar'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Checkbox from 'primevue/checkbox'

const route = useRoute()
const toast = useToast()
const progress = useProgressStore()

const courseId = computed(() => Number(route.params.courseId || 1))

const lessons = computed(() => progress.lessons || [])

const remainingLessons = computed(() =>
  Math.max(progress.totalLessons - progress.completedLessons, 0)
)
function isLessonCompleted(lessonId) {
  return progress.completedLessonIds.includes(lessonId)
}

async function loadProgress() {
  try {
    await Promise.all([
      progress.fetchCourseProgress(courseId.value),
      progress.fetchLessons(courseId.value)
    ])
  } catch {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Unable to load course progress.',
      life: 3000
    })
  }
}

async function onLessonToggle(lessonId,data) {
  if (isLessonCompleted(lessonId)) {
    toast.add({
      severity: 'info',
      summary: 'Not supported',
      detail: 'Unmarking a lesson is not supported in this demo.',
      life: 2500
    })
    return
  }

  try {
    await progress.completeLesson(lessonId)
    toast.add({
      severity: 'success',
      summary: 'Lesson completed',
      detail: `Lesson ${data.title} marked as complete.`,
      life: 2000
    })
  } catch {
    // store already shows error toast
  }
}

onMounted(() => {
  loadProgress()
})
</script>

<style scoped>
.page {
  padding: 1.25rem 1.5rem 2rem;
}

/* Card + toolbar matches Courses view */
.table-card {
  background: radial-gradient(circle at top left, #eff6ff 0, #ffffff 40%);
  border-radius: 1rem;
  border: 1px solid #e5e7eb;
  padding: 0.9rem 0.9rem 0.8rem;
  box-shadow: 0 18px 45px rgba(15, 23, 42, 0.08);
}

.table-toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 0.75rem;
}

.toolbar-left {
  display: flex;
  flex-direction: column;
}

.page-title {
  font-size: 1.4rem;
  font-weight: 700;
  color: #111827;
  margin: 0;
}

.course-name {
  font-size: 1rem;
  font-weight: 500;
  color: #2563eb;
}

.page-subtitle {
  margin: 0.25rem 0 0;
  font-size: 0.85rem;
  color: #6b7280;
}

.toolbar-right {
  display: flex;
  align-items: center;
}

.status-pill {
  display: inline-flex;
  align-items: center;
  padding: 0.25rem 0.6rem;
  border-radius: 999px;
  font-size: 0.75rem;
  background: #ecfdf5;
  color: #15803d;
  border: 1px solid #bbf7d0;
}

.loading-pill {
  background: #eff6ff;
  color: #1d4ed8;
  border-color: #bfdbfe;
}

/* Progress overview */
.progress-overview {
  margin-bottom: 1rem;
}

.progress-main {
  background: #f9fafb;
  border-radius: 0.75rem;
  padding: 0.75rem 0.85rem;
  border: 1px solid #e5e7eb;
}

.progress-label-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 0.35rem;
}

.progress-label {
  font-size: 0.78rem;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  font-weight: 600;
  color: #6b7280;
}

.progress-value {
  font-size: 0.86rem;
  font-weight: 600;
  color: #111827;
}

.progress-bar :deep(.p-progressbar) {
  border-radius: 999px;
}

.progress-bar {
  height: 0.65rem;
  margin-bottom: 0.45rem;
}

.progress-bar-text {
  font-size: 0.75rem;
  color: #374151;
  margin-left: 0.25rem;
}

.progress-stats {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-top: 0.35rem;
}

.stat-chip {
  display: flex;
  flex-direction: column;
  padding: 0.35rem 0.6rem;
  border-radius: 0.6rem;
  background: #ffffff;
  border: 1px solid #e5e7eb;
  min-width: 90px;
}

.stat-label {
  font-size: 0.72rem;
  color: #6b7280;
}

.stat-value {
  font-size: 0.9rem;
  font-weight: 600;
  color: #111827;
}

.stat-value.success {
  color: #15803d;
}

.stat-value.warn {
  color: #b45309;
}

/* Lessons list */
.lessons-header {
  display: flex;
  flex-direction: column;
  gap: 0.15rem;
  margin: 0.5rem 0 0.35rem;
}

.lessons-title {
  font-size: 0.9rem;
  font-weight: 600;
  color: #111827;
  margin: 0;
}

.lessons-subtitle {
  font-size: 0.8rem;
  color: #6b7280;
}

.lessons-table :deep(.p-datatable-thead > tr > th) {
  font-size: 0.78rem;
  text-transform: uppercase;
  letter-spacing: 0.09em;
  color: #6b7280;
  background: #f9fafb;
}

.lessons-table :deep(.p-datatable-tbody > tr > td) {
  font-size: 0.86rem;
}

.lesson-info {
  display: flex;
  flex-direction: column;
}

.lesson-title {
  font-weight: 500;
  color: #111827;
}

.lesson-title.completed {
  text-decoration: line-through;
  color: #6b7280;
}

.lesson-meta {
  font-size: 0.75rem;
  color: #9ca3af;
}

/* Status chips */
.status-chip {
  display: inline-flex;
  align-items: center;
  gap: 0.25rem;
  padding: 0.2rem 0.55rem;
  border-radius: 999px;
  font-size: 0.75rem;
  border: 1px solid transparent;
}

.status-chip-completed {
  background: #ecfdf5;
  color: #15803d;
  border-color: #bbf7d0;
}

.status-chip-pending {
  background: #fefce8;
  color: #b45309;
  border-color: #fef3c7;
}

.chip-icon {
  font-size: 0.9rem;
}

/* Empty / loading states */
.empty-state,
.loading-state {
  padding: 1.5rem 1rem;
  text-align: center;
  color: #6b7280;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.35rem;
}

.empty-icon {
  font-size: 1.5rem;
  color: #9ca3af;
}

.loading-icon {
  font-size: 1.5rem;
  color: #2563eb;
}

/* Mobile */
@media (max-width: 768px) {
  .page {
    padding-inline: 1rem;
  }

  .table-toolbar {
    flex-direction: column;
    align-items: flex-start;
  }

  .progress-stats {
    flex-wrap: wrap;
  }
}
</style>
