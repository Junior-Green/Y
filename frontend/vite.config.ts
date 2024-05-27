import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': "/src",
    }
  },
  build: {
    outDir: '../backend/src/main/resources/static', // Output the dist folder to this location
  },
  envPrefix: 'REACT_APP_',
})
