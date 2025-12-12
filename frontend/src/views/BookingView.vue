<template>
  <div class="page">
    <section class="table-card">
      <!-- Header -->
      <div class="table-toolbar">
        <div class="toolbar-left">
          <div>
            <h1 class="page-title">
              Training & Lab Bookings
            </h1>
            <p class="page-subtitle">
              Browse upcoming trainings and reserve your seat.
            </p>
          </div>
        </div>
        <div class="toolbar-right">
          <RouterLink to="/my-bookings" class="link-button">
            <Button
              label="My Bookings"
              icon="pi pi-calendar"
              size="small"
              outlined
            />
          </RouterLink>
        </div>
      </div>

      <!-- Trainings table -->
      <DataTable
        :value="trainings"
        dataKey="id"
        class="trainings-table"
        responsiveLayout="scroll"
        :loading="store.trainingsLoading"
        stripedRows
      >
        <template #empty>
          <div class="empty-state">
            <i class="pi pi-folder-open empty-icon"></i>
            <p>No trainings are currently scheduled.</p>
          </div>
        </template>

        <template #loading>
          <div class="loading-state">
            <i class="pi pi-spin pi-spinner loading-icon"></i>
            <p>Loading trainings...</p>
          </div>
        </template>

        <Column field="title" header="Training">
          <template #body="{ data }">
            <div class="training-info">
              <div class="training-title">{{ data.title }}</div>
              <div class="training-description">
                {{ data.description }}
              </div>
            </div>
          </template>
        </Column>

        <Column field="startDateTime" header="Schedule" style="width: 14rem">
          <template #body="{ data }">
            <div class="schedule">
              <span class="schedule-date">
                {{ formatDate(data.startDateTime) }}
              </span>
              <span class="schedule-time">
                {{ formatTime(data.startDateTime) }}
              </span>
            </div>
          </template>
        </Column>

        <Column header="Seats" style="width: 11rem">
          <template #body="{ data }">
            <div class="seats-info">
              <span class="seats-chip">
                {{ data.bookedCount }}/{{ data.capacity }} booked
              </span>
              <span
                :class="[
                  'availability-chip',
                  data.availableSeats > 0 ? 'available' : 'full'
                ]"
              >
                <i
                  :class="[
                    'pi',
                    data.availableSeats > 0 ? 'pi-check-circle' : 'pi-times-circle'
                  ]"
                />
                {{ data.availableSeats > 0 ? data.availableSeats + ' seats left' : 'Full' }}
              </span>
            </div>
          </template>
        </Column>

        <Column header="Action" style="width: 9rem">
          <template #body="{ data }">
            <Button
              label="Book"
              icon="pi pi-ticket"
              size="small"
              :disabled="isBookDisabled(data)"
              :loading="store.isBookingInProgress(data.id)"
              @click="onBookClick(data)"
            />
          </template>
        </Column>
      </DataTable>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import { useToast } from 'primevue/usetoast'
import { useBookingStore } from '@/stores/bookingStore.js'

import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'

const toast = useToast()
const store = useBookingStore()

const trainings = computed(() => store.trainings || [])

// Helpers
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

function isBookDisabled(training) {
  if (!training) return true
  const noSeats = !training.availableSeats || training.availableSeats <= 0
  const inProgress = store.isBookingInProgress(training.id)
  return noSeats || inProgress
}

async function onBookClick(training) {
    let response  = await store.bookTraining(training.id);
    console.log("respnse : ",response);
    if(response.success == false){
        toast.add({
          severity: 'error',
          summary: 'Booked',
          detail: response.message,
          life: 2500
        })
    }else{
        toast.add({
          severity: 'success',
          summary: 'Booked',
          detail: `You have booked "${training.title}".`,
          life: 2500
        })
    }
  
}

onMounted(() => {
  store.fetchTrainings()
})
</script>

<style scoped>
.page {
  padding: 1.25rem 1.5rem 2rem;
}

/* Card + toolbar (same as others) */
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

/* Table styling */
.trainings-table :deep(.p-datatable-thead > tr > th) {
  font-size: 0.78rem;
  text-transform: uppercase;
  letter-spacing: 0.09em;
  color: #6b7280;
  background: #f9fafb;
}

.trainings-table :deep(.p-datatable-tbody > tr > td) {
  font-size: 0.86rem;
}

/* Training details */
.training-info {
  display: flex;
  flex-direction: column;
  gap: 0.15rem;
}

.training-title {
  font-weight: 600;
  color: #111827;
}

.training-description {
  font-size: 0.8rem;
  color: #6b7280;
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

/* Seats & availability */
.seats-info {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.seats-chip {
  font-size: 0.8rem;
  color: #374151;
}

.availability-chip {
  display: inline-flex;
  align-items: center;
  gap: 0.25rem;
  padding: 0.16rem 0.55rem;
  border-radius: 999px;
  font-size: 0.75rem;
  border: 1px solid transparent;
}

.availability-chip.available {
  background: #ecfdf5;
  color: #15803d;
  border-color: #bbf7d0;
}

.availability-chip.full {
  background: #fef2f2;
  color: #b91c1c;
  border-color: #fecaca;
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
