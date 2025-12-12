<template>
  <div class="page">

    <section class="table-card">
      <!-- Top toolbar: title + global search -->
      <div class="table-toolbar">
        <div class="toolbar-left">
          <div>
            <h1 class="page-title">
              Courses Catalog

          <span class="table-subtitle">
           ( <span class="dot"></span>
            {{ totalCourses }} courses found )
          </span>
            </h1>
            <p class="page-subtitle">
              Browse and manage all trainings available in HawkStack Academy.
            </p>
          </div>
        </div>
        <div class="toolbar-right">
        </div>
      </div>

      <DataTable
      :totalRecords="totalCourses"
      stripedRows 
      lazy
    :value="courses"
    :loading="loading"
    dataKey="id"
    filterDisplay="row"
    class="courses-table"
    :rows="pagination.elementsPerPage"
    :rowsPerPageOptions="rowsPerPageOptions"
    :first="(pagination.currentPage) * pagination.elementsPerPage"
    paginator
    paginatorTemplate="RowsPerPageDropdown FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink"
    currentPageReportTemplate="{first} to {last} of {totalRecords}"
    @page="onPageEvent"
    @sort="onSortEvent"
    :sortField="sortBy.field"
    :sortOrder="sortBy.order"
  >
        <template #empty>
          <div class="empty-state">
            <i class="pi pi-folder-open empty-icon"></i>
            <p>No courses found for the current filters.</p>
          </div>
        </template>

        <template #loading>
          <div class="loading-state">
            <i class="pi pi-spin pi-spinner loading-icon"></i>
            <p>Loading courses...</p>
          </div>
        </template>

        <!-- Title column -->
        <Column field="title" header="Title" sortable>
          <!-- no per-column filter here -->
           <template #filter>
            
          <span class="p-input-icon-left search-input-wrapper">

            <IconField>
              <InputIcon class="pi pi-search" />
              
              <InputText
              size="small"
                v-model="filters.search"
                placeholder="Search by course title..."
                class="search-input"
              />
            </IconField>
          </span></template>
        </Column>

        <!-- Category column with header filter in 2nd row -->
        <Column field="category" header="Category" sortable style="width: 12rem">
          <template #filter>
            <Dropdown
            size="small"
              v-model="filters.category"
              :options="categoryOptions"
              optionLabel="label"
              optionValue="value"
              placeholder="All"
              class="column-dropdown"
              showClear
              @change="onFiltersChanged"
            />
          </template>
        </Column>

        <!-- Level column with header filter + colored pills -->
        <Column field="level" header="Level" sortable style="width: 10rem">
          <template #body="{ data }">
            <span :class="['level-pill', levelClass(data.level)]">
              {{ data.level }}
            </span>
          </template>
          <template #filter>
            <Dropdown
            size="small"
              v-model="filters.level"
              :options="levelOptions"
              optionLabel="label"
              optionValue="value"
              placeholder="All"
              class="column-dropdown"
              showClear
              @change="onFiltersChanged"
            />
          </template>
        </Column>

        <Column header="Actions" style="width: 9rem">
          <template #body="{ data }">
            <Button
              label="Progress"
              icon="pi pi-chart-line"
              size="small"
              outlined
              @click="goToProgress(data)"
            />
          </template>
      </Column>

      </DataTable>
    </section>
  </div>
</template>

<script setup>
import { reactive, computed, onMounted, watch } from 'vue'
import { useToast } from 'primevue/usetoast'
import { useCatalogStore } from '@/stores/catalogStore.js'
import { useRouter } from 'vue-router'
const toast = useToast()
const catalogStore = useCatalogStore()
const router = useRouter()


// Data from store
const courses = computed(() => catalogStore.courses)
const totalCourses = computed(() => catalogStore.totalCourses || 0)
const loading = computed(() => catalogStore.loading)

const filters = reactive({
  category: null,
  level: null,
  search: ''
})

// 0-based page index for backend
const pagination = reactive({
  currentPage: 0,
  elementsPerPage: 5
})

const sortBy = reactive({
  field: 'title',
  order: 1
})

const totalPages = computed(() =>
  pagination.elementsPerPage > 0
    ? Math.ceil(totalCourses.value / pagination.elementsPerPage)
    : 1
)

const categoryOptions = [
  { label: 'All categories', value: null },
  { label: 'Cloud', value: 'Cloud' },
  { label: 'DevOps', value: 'DevOps' },
  { label: 'Security', value: 'Security' },
  { label: 'Backend', value: 'Backend' },
  { label: 'Frontend', value: 'Frontend' },
  { label: 'Database', value: 'Database' }
]

const levelOptions = [
  { label: 'All levels', value: null },
  { label: 'Beginner', value: 'Beginner' },
  { label: 'Intermediate', value: 'Intermediate' },
  { label: 'Advanced', value: 'Advanced' }
]

const pageSizeOptions = [
  { label: '5', value: 5 },
  { label: '10', value: 10 },
  { label: '20', value: 20 },
  { label: '50', value: 50 }
]

const rowsPerPageOptions = [5, 10, 20, 50]

// Helpers
function encodeSearch(value) {
  return value ? btoa(value.trim()) : null
}
function goToProgress(course) {
  if (!course?.id) {
    toast.add({
      severity: 'warn',
      summary: 'Missing ID',
      detail: 'Course ID is not available.',
      life: 2000
    })
    return
  }
  router.push({ name: 'progress', params: { courseId: course.id } })
}
function buildQueryPayload() {
  return {
    category: filters.category,
    level: filters.level,
    search: encodeSearch(filters.search),
    pageNumber: pagination.currentPage, // 0-based
    elementsPerPage: pagination.elementsPerPage,
    sortBy: `${sortBy.field}:${sortBy.order === 1 ? 'ASC' : 'DESC'}`
  }
}

async function fetchCourses() {
  try {
    const payload = buildQueryPayload()
    await catalogStore.fetchCourses(payload)
  } catch (error) {
    toast.add({
      severity: 'error',
      summary: 'Failed to load courses',
      detail: 'Something went wrong while fetching courses. Please try again.',
      life: 4000
    })
    console.error(error)
  }
}

// Filtering / pagination events
function onFiltersChanged() {
  pagination.currentPage = 0
  fetchCourses()
}

function onPageSizeChange() {
  pagination.currentPage = 0
  fetchCourses()
}

function onPageEvent(event) {
  // event.page is 0-based, event.rows is page size
  pagination.currentPage = event.page
  pagination.elementsPerPage = event.rows
  fetchCourses()
}

function onSortEvent(event) {
  sortBy.field = event.sortField
  sortBy.order = event.sortOrder
  fetchCourses()
}

// Level pill styling
function levelClass(level) {
  if (!level) return 'level-default'
  const l = String(level).toLowerCase()
  if (l === 'beginner') return 'level-beginner'
  if (l === 'intermediate') return 'level-intermediate'
  if (l === 'advanced') return 'level-advanced'
  return 'level-default'
}

// Debounced search (1.5s)
let searchTimeout = null
watch(
  () => filters.search,
  () => {
    clearTimeout(searchTimeout)
    searchTimeout = setTimeout(() => {
      pagination.currentPage = 0
      fetchCourses()
    }, 1500)
  }
)

onMounted(async () => {
  await fetchCourses()
})
</script>

<style scoped>
.page {
  /* max-width: 1200px; */
  /* margin: 0 auto; */
  padding: 1.25rem 1.5rem 2rem;
}

.page-header {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 1.25rem;
}

.page-title {
  font-size: 1.4rem;
  font-weight: 700;
  color: #111827;
  margin: 0;
}

.page-subtitle {
  margin: 0.15rem 0 0;
  font-size: 0.85rem;
  color: #6b7280;
}

.page-header-actions {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

/* Table card + toolbar */
.table-card {
  background: radial-gradient(circle at top left, #eff6ff 0, #ffffff 40%);
  border-radius: 1rem;
  border: 1px solid #e5e7eb;
  padding: 0.9rem 0.9rem 0.6rem;
  box-shadow: 0 18px 45px rgba(15, 23, 42, 0.08);
}

.table-toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 0.5rem;
}

.toolbar-left {
  display: flex;
  flex-direction: column;
}

.table-title {
  font-size: 1rem;
  font-weight: 600;
  color: #111827;
}

.table-subtitle {
  font-size: 0.8rem;
  color: #6b7280;
  display: inline-flex;
  align-items: center;
  gap: 0.3rem;
}

.table-subtitle .dot {
  width: 0.4rem;
  height: 0.4rem;
  border-radius: 999px;
  background: #22c55e;
}

.toolbar-right {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.search-input-wrapper {
  width: 260px;
}

.search-input {
  width: 100%;
}

/* Column header filters: use column filter slot styling */
.column-dropdown {
  width: 100%;
}

/* DataTable styling */
.courses-table :deep(.p-datatable-thead > tr:nth-child(1) > th) {
  font-size: 0.78rem;
  text-transform: uppercase;
  letter-spacing: 0.09em;
  color: #6b7280;
  background: #f9fafb;
}

.courses-table :deep(.p-datatable-thead > tr:nth-child(2) > th) {
  background: #f9fafb;
  padding-top: 0.25rem;
  padding-bottom: 0.35rem;
}

.courses-table :deep(.p-column-filter) {
  width: 100%;
}

.courses-table :deep(.p-datatable-tbody > tr > td) {
  font-size: 0.86rem;
}

/* Level pill */
.level-pill {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.1rem 0.55rem;
  border-radius: 999px;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: capitalize;
}

.level-beginner {
  background: rgba(34, 197, 94, 0.08);
  color: #15803d;
}

.level-intermediate {
  background: rgba(59, 130, 246, 0.08);
  color: #1d4ed8;
}

.level-advanced {
  background: rgba(249, 115, 22, 0.08);
  color: #c2410c;
}

.level-default {
  background: #e5e7eb;
  color: #374151;
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

/* Unified footer below table/paginator */
.table-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: 0.5rem;
  margin-top: 0.4rem;
  border-top: 1px dashed #e5e7eb;
  font-size: 0.8rem;
  color: #6b7280;
}

.footer-right {
  display: flex;
  align-items: center;
  gap: 0.4rem;
}

.page-size-dropdown {
  min-width: 4.5rem;
}

/* Mobile */
@media (max-width: 768px) {
  .page {
    padding-inline: 1rem;
  }

  .page-header {
    flex-direction: column;
    align-items: flex-start;
  }

  .table-toolbar {
    flex-direction: column;
    align-items: flex-start;
  }

  .search-input-wrapper {
    width: 100%;
  }

  .table-footer {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.3rem;
  }
}
</style>
