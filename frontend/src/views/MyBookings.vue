<template>
  <div class="page">
    <section class="table-card">
      <!-- Header -->
      <div class="table-toolbar">
        <div class="toolbar-left">
          <div>
            <h1 class="page-title">
              My Bookings
            </h1>
            <p class="page-subtitle">
              View your upcoming and past training reservations.
            </p>
          </div>
        </div>
        <div class="toolbar-right">
          <RouterLink to="/booking" class="link-button">
            <Button
              label="Back to Trainings"
              icon="pi pi-arrow-left"
              size="small"
              outlined
            />
          </RouterLink>
        </div>
      </div>

      <!-- Total summary -->
      <div class="summary-row" v-if="totalBookings > 0">
        <span class="summary-text">
          You have <strong>{{ totalBookings }}</strong> bookings.
        </span>
      </div>

      <!-- Bookings table -->
      <DataTable
        :value="bookings"
        dataKey="id"
        class="bookings-table"
        responsiveLayout="scroll"
        :loading="store.bookingsLoading"
        stripedRows
      >
        <template #empty>
          <div class="empty-state">
            <i class="pi pi-calendar-times empty-icon"></i>
            <p>No bookings found for your account.</p>
          </div>
        </template>

        <template #loading>
          <div class="loading-state">
            <i class="pi pi-spin pi-spinner loading-icon"></i>
            <p>Loading your bookings...</p>
          </div>
        </template>

        <Column field="trainingTitle" header="Training">
          <template #body="{ data }">
            <div class="training-info">
              <div class="training-title">{{ data.trainingTitle }}</div>
              <div class="training-meta">
                Booking ID: {{ data.id }}
              </div>
            </div>
          </template>
        </Column>

        <Column field="trainingStartDateTime" header="Schedule" style="width: 14rem">
          <template #body="{ data }">
            <div class="schedule">
              <span class="schedule-date">
                {{ formatDate(data.trainingStartDateTime) }}
              </span>
              <span class="schedule-time">
                {{ formatTime(data.trainingStartDateTime) }}
              </span>
            </div>
          </template>
        </Column>

        <Column field="createdAt" header="Booked On" style="width: 14rem">
          <template #body="{ data }">
            <div class="schedule">
              <span class="schedule-date">
                {{ formatDate(data.createdAt) }}
              </span>
              <span class="schedule-time">
                {{ formatTime(data.createdAt) }}
              </span>
            </div>
          </template>
        </Column>
      </DataTable>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import { useBookingStore } from '@/stores/bookingStore.js'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'

const store = useBookingStore()

const bookings = computed(() => store.bookings || [])
const totalBookings = computed(() => store.totalBookings || 0)
const DEMO_USER_ID = 1

function formatDate(dateTime) {
  if (!dateTime) return '-'
  const d = new Date(dateTime)
  return d.toLocaleDateString(undefined, {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

function formatTime(dateTime) {
  if (!dateTime) return ''
  const d = new Date(dateTime)
  return d.toLocaleTimeString(undefined, {
    hour: '2-digit',
    minute: '2-digit'
  })
}

onMounted(() => {
  store.fetchUserBookings(DEMO_USER_ID)
})
</script>

<style scoped>
.page {
  padding: 1.25rem 1.5rem 2rem;
}

/* Card + toolbar */
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

.page-subtitle {
  margin: 0.25rem 0 0;
  font-size: 0.85rem;
  color: #6b7280;
}

.toolbar-right {
  display: flex;
  align-items: center;
}

.link-button {
  text-decoration: none;
}

/* Summary */
.summary-row {
  margin-bottom: 0.5rem;
}

.summary-text {
  font-size: 0.85rem;
  color: #4b5563;
}

/* Table styling */
.bookings-table :deep(.p-datatable-thead > tr > th) {
  font-size: 0.78rem;
  text-transform: uppercase;
  letter-spacing: 0.09em;
  color: #6b7280;
  background: #f9fafb;
}

.bookings-table :deep(.p-datatable-tbody > tr > td) {
  font-size: 0.86rem;
}

/* Training details */
.training-info {
  display: flex;
  flex-direction: column;
}

.training-title {
  font-weight: 600;
  color: #111827;
}

.training-meta {
  font-size: 0.78rem;
  color: #9ca3af;
}

/* Schedule */
.schedule {
  display: flex;
  flex-direction: column;
}

.schedule-date {
  font-size: 0.82rem;
  font-weight: 500;
  color: #111827;
}

.schedule-time {
  font-size: 0.78rem;
  color: #6b7280;
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
}
</style>
