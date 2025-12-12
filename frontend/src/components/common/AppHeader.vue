<template>
  <header class="app-header">
    <div class="header-inner">
      <!-- Left: Logo + Brand -->
      <div class="brand" @click="$router.push('/courses')">
        <img src="/hawkstack.png" alt="HawkStack Technologies" class="brand-logo" />
        <div class="brand-text">
          <div class="brand-name">HawkStack Technologies</div>
          <div class="brand-tagline">Logic, Innovation, Insight</div>
        </div>
      </div>

      <!-- Center: Navigation -->
      <nav class="nav-links">
        <RouterLink
          to="/courses"
          class="nav-link"
          :class="{ active: currentPath === '/courses' || currentPath === '/' }"
        >
          <i class="pi pi-book mr-1"></i>
          Courses <span class="asignment-number">(Assignement-3)</span>
        </RouterLink>
        <RouterLink
          to="/progress/1"
          class="nav-link"
          :class="{ active: currentPath.includes('progress') }"
        >
          <i class="pi pi-chart-line mr-1"></i>
          Progress <span class="asignment-number">(Assignement-1)</span>
        </RouterLink>
        <RouterLink
          to="/booking"
          class="nav-link"
          :class="{ active: currentPath === '/booking' }"
        >
          <i class="pi pi-calendar mr-1"></i>
          Trainings <span class="asignment-number">(Assignement-2)</span>
        </RouterLink>
      </nav>

      <!-- Right: User profile chip + dropdown -->
      <div class="header-right">
        <div class="user-chip">
          <div class="user-avatar">
            <span>JD</span>
          </div>
          <div class="user-info">
            <div class="user-name">John Doe</div>
            <div class="user-role">Demo User</div>
          </div>
          <i class="pi pi-chevron-down user-chevron" :class="{ open: profileMenuOpen }"></i>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { RouterLink, useRoute } from 'vue-router'
import { computed, ref, onMounted, onBeforeUnmount } from 'vue'

const route = useRoute()
const currentPath = computed(() => route.path)

// simple local state for profile dropdown
const profileMenuOpen = ref(false)

function toggleProfileMenu() {
  profileMenuOpen.value = !profileMenuOpen.value
}

function closeProfileMenu(e) {
  // close when clicked outside
  if (!e.target.closest('.header-right')) {
    profileMenuOpen.value = false
  }
}

onMounted(() => {
  window.addEventListener('click', closeProfileMenu)
})

onBeforeUnmount(() => {
  window.removeEventListener('click', closeProfileMenu)
})
</script>

<style scoped>
.asignment-number{
  font-size: 0.75rem;
  color: #313131;
  margin-left: 0.25rem;
}
.app-header {
  position: sticky;
  top: 0;
  z-index: 100;
  background: #ffffff;
  border-bottom: 1px solid #e5e7eb;
  box-shadow: 0 2px 4px rgba(15, 23, 42, 0.05);
}

.header-inner {
  padding: 0.6rem 1.5rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1.5rem;
}

/* Brand */
.brand {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;
}

.brand-logo {
  height: 32px;
  width: auto;
  object-fit: contain;
}

.brand-text {
  display: flex;
  flex-direction: column;
}

.brand-name {
  font-size: 1.05rem;
  font-weight: 700;
  letter-spacing: 0.05em;
  color: #2563eb;
  text-transform: uppercase;
}

.brand-tagline {
  font-size: 0.72rem;
  color: #047857;
  text-transform: uppercase;
  letter-spacing: 0.18em;
}

/* Nav */
.nav-links {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  flex: 1;
  justify-content: center;
}

.nav-link {
  display: inline-flex;
  align-items: center;
  gap: 0.25rem;
  padding: 0.35rem 0.9rem;
  border-radius: 999px;
  font-size: 0.9rem;
  font-weight: 500;
  color: #4b5563;
  text-decoration: none;
  transition: all 0.18s ease;
}

.nav-link:hover {
  background-color: #eff6ff;
  color: #1d4ed8;
}

.nav-link.active {
  background-color: #2563eb;
  color: #ffffff;
}

/* Right: user chip */
.header-right {
  position: relative;
  display: flex;
  align-items: center;
}

.user-chip {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.25rem 0.75rem 0.25rem 0.4rem;
  border-radius: 999px;
  background: #f9fafb;
  border: 1px solid #e5e7eb;
  cursor: pointer;
  transition: all 0.18s ease;
}

.user-chip:hover {
  background: #eff6ff;
  border-color: #bfdbfe;
  box-shadow: 0 2px 6px rgba(15, 23, 42, 0.08);
}

.user-avatar {
  height: 28px;
  width: 28px;
  border-radius: 999px;
  background: #2563eb;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #ffffff;
  font-size: 0.8rem;
  font-weight: 600;
}

.user-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.user-name {
  font-size: 0.85rem;
  font-weight: 600;
  color: #111827;
}

.user-role {
  font-size: 0.7rem;
  color: #6b7280;
}

.user-chevron {
  font-size: 0.75rem;
  color: #6b7280;
  transition: transform 0.18s ease;
}

.user-chevron.open {
  transform: rotate(180deg);
}

/* Dropdown menu */
.user-menu {
  position: absolute;
  right: 0;
  top: 110%;
  min-width: 180px;
  background: #ffffff;
  border-radius: 0.75rem;
  border: 1px solid #e5e7eb;
  box-shadow: 0 10px 25px rgba(15, 23, 42, 0.12);
  padding: 0.25rem 0.25rem;
  display: flex;
  flex-direction: column;
  z-index: 200;
}

.user-menu-item {
  width: 100%;
  text-align: left;
  padding: 0.4rem 0.75rem;
  border-radius: 0.6rem;
  background: transparent;
  border: none;
  font-size: 0.85rem;
  color: #374151;
  display: flex;
  align-items: center;
  cursor: pointer;
  transition: background 0.15s ease, color 0.15s ease;
}

.user-menu-item:hover {
  background: #eff6ff;
  color: #1d4ed8;
}

.user-menu-item.danger {
  color: #b91c1c;
}

.user-menu-item.danger:hover {
  background: #fee2e2;
  color: #b91c1c;
}

/* Simple fade animation */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.15s ease, transform 0.15s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(-4px);
}

/* Mobile */
@media (max-width: 768px) {
  .header-inner {
    flex-direction: column;
    align-items: stretch;
    gap: 0.75rem;
  }

  .nav-links {
    justify-content: flex-start;
    flex-wrap: wrap;
  }

  .brand {
    justify-content: flex-start;
  }

  .header-right {
    align-self: flex-end;
  }
}
</style>
