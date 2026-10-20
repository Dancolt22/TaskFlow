// Import defineConfig helper from Vite for full type hints and IDE autocomplete support
import { defineConfig } from "vite";
// Import the official Vite React plugin to support JSX transformation and Fast Refresh
import react from "@vitejs/plugin-react";

/**
 * Vite Configuration (vite.config.js)
 * Sets up development server options, plugin pipelines, and bundling settings for the client.
 */
export default defineConfig({
  // Register the React plugin so Vite compiles JSX and updates changed components instantly without losing state
  plugins: [react()],
});
