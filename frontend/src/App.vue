<template>
  <div>
    <!-- Global Toast (KEEP - Perfect!) -->
    <Toast />
    <AppHeader></AppHeader>
    <!-- Router View will replace this content -->
    <RouterView />
  </div>
</template>

<script setup>
import AppHeader from './components/common/AppHeader.vue';
import { useToast } from 'primevue/usetoast';
const toast = useToast();
function clicked(){
 toast.add({ severity: 'info', summary: 'Info', detail: 'Message Content', life: 3000 });

}
</script>

<style>
</style>
