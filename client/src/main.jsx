// Import React core library to enable JSX transformation and component lifecycle
import React from "react";
// Import ReactDOM client library to attach React into the actual browser DOM tree
import ReactDOM from "react-dom/client";
// Import our main root React component containing the entire layout
import App from "./App.jsx";
// Import global CSS styles that apply across the entire application
import "./index.css";

/**
 * Application Entry Point (main.jsx)
 * This script runs when the browser loads index.html and starts our React app.
 */

// Find the HTML div with id="root" and create a React root container for it
ReactDOM.createRoot(document.getElementById("root")).render(
  // StrictMode enables additional checks and warnings during development to catch bugs early
  <React.StrictMode>
    {/* Render the top-level App component into the page */}
    <App />
  </React.StrictMode>
);
