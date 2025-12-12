<template>
    <div class="ai-native-error-page">
      <div class="background-error-message">404</div>
      <div class="foreground-error-message">
        <h1 class="error-content">Oops! Page not found.</h1>
        <p class="error-message-content">
          The page you are looking for might have been removed, had its name
          changed or is temporarily unavailable.
        </p>
        <div class="error-page-buttons">
          <Button
            variant="outlined"
            class="btn-404-back mr-sm"
            icon="pi pi-arrow-circle-left"
            severity="secondary"
            label="Back"
            size="large"
            raised
            @click="$router.go(-1)"
          />
          <Button
            class="btn-404-home q-mr-sm"
            icon="pi pi-home"
            label="Home"
            size="large"
            raised
            @click="$router.push('/')"
          />
        </div>
      </div>
    </div>
</template>
  
<script setup>
</script>

<style>
.ai-native-error-page {
  position: relative;
  text-align: center;
  color:#2563eb;
  height: calc(100vh - 100px);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden !important;
}

.background-error-message {
  font-size: 40rem;
  color:#a2bcf3c4;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1;
  font-family: 'Saira', sans-serif !important;
  font-weight: 600;
}

.foreground-error-message {
  z-index: 2;
  color: var(--q-txt-invert);
  width: 34.81rem;
}

.error-content {
  font-size: 2rem;
  margin-bottom: 1rem;

  font-weight: 500;
}
.bg-grey {
  background: var(--q-bg-form) !important;
}

.error-message-content {
  margin-bottom: 2rem;
  font-size: 1.125rem;
  font-weight: 400;
  line-height: 1.35rem;
}

.error-page-buttons {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
}

</style>
  