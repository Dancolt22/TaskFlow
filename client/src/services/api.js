/**
 * API Service Layer (api.js)
 * Centralizes all HTTP communication between the React frontend and the Express backend.
 * Keeps components clean so they don't have to repeat URLs, fetch options, or error parsing.
 */

// Read the backend base URL from Vite environment variables (prefixed with VITE_), or default to localhost:5000
const API_URL = import.meta.env.VITE_API_URL || "http://localhost:5000";

/**
 * Core request helper that handles network communication and response validation.
 * @param {string} path - The API endpoint relative path (e.g. "/api/tasks")
 * @param {object} options - Fetch configuration options (headers, method, body)
 * @returns {Promise<any>} - Parsed JSON response data
 */
async function request(path, options) {
  // Variable to store the raw HTTP response object from fetch
  let response;

  try {
    // Send the HTTP request to the combined backend URL and endpoint path
    response = await fetch(`${API_URL}${path}`, options);
  } catch {
    // If fetch itself throws, the backend server is unreachable, offline, or experiencing CORS issues
    throw new Error("Cannot reach the server. Check that it is running.");
  }

  // Parse the JSON data sent back from the backend response stream
  const data = await response.json();

  // Check if the response status code is outside the successful 200-299 range
  if (!response.ok) {
    // Throw an error using the server's error message, or fallback to a generic message
    throw new Error(data.message || "Something went wrong.");
  }

  // Return the parsed JSON data for the caller component to use
  return data;
}

/**
 * Helper function specifically for POST requests that send JSON data.
 * @param {string} path - Endpoint path (e.g. "/api/tasks" or "/api/signup")
 * @param {object} body - JavaScript object containing the payload to serialize
 * @returns {Promise<any>} - Parsed JSON response from request()
 */
function postJson(path, body) {
  // Call request() configured with POST method, JSON headers, and stringified body
  return request(path, {
    // Set the HTTP verb to POST for creating new records
    method: "POST",
    // Tell the server we are sending JSON data in the request body
    headers: { "Content-Type": "application/json" },
    // Convert the JavaScript object into a JSON string
    body: JSON.stringify(body),
  });
}

// Exported API helper functions used directly inside our React components:

// Sends an HTTP GET request to /api/tasks to fetch all saved tasks from MongoDB
export const getTasks = () => request("/api/tasks");

// Sends an HTTP POST request to /api/tasks with the new task payload to save in MongoDB
export const createTask = (task) => postJson("/api/tasks", task);

// Sends an HTTP POST request to /api/signup with the new user credentials to register an account
export const createSignup = (details) => postJson("/api/signup", details);
