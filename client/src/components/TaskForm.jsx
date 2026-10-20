// Import useState hook from React to manage controlled component state
import { useState } from "react";
// Import our API service function to send task data to the backend
import { createTask } from "../services/api.js";
// Import our reusable feedback Message component
import Message from "./Message.jsx";

/**
 * TaskForm Component
 * A controlled form component that captures task details and saves them to MongoDB.
 * Demonstrates React state, form event handling, validation, and API integration.
 * @param {Function} onSaved - Callback prop executed after saving to refresh task list in App.jsx
 */
function TaskForm({ onSaved }) {
  // State for the task title input field
  const [title, setTitle] = useState("");
  // State for the multi-line task description textarea
  const [description, setDescription] = useState("");
  // State for the priority select dropdown (defaults to "Medium")
  const [priority, setPriority] = useState("Medium");
  // State for the due date input field
  const [dueDate, setDueDate] = useState("");

  // State to track if an API request is currently in progress (disables submit button)
  const [saving, setSaving] = useState(false);
  // State to store error messages returned from validation or the server
  const [error, setError] = useState("");
  // State to store success message once the task is saved
  const [success, setSuccess] = useState("");

  // Handler function triggered when the user submits the form
  async function handleSubmit(event) {
    // Prevent the default browser form submission that reloads the entire page
    event.preventDefault();

    // Clear any previous error message from prior submission attempts
    setError("");
    // Clear any previous success message from prior submission attempts
    setSuccess("");

    // Validate that the title field is not empty or just whitespace
    if (!title.trim()) {
      // Set an error message if title is missing
      setError("Give your task a title.");
      // Stop further function execution early
      return;
    }

    // Validate that a due date was chosen
    if (!dueDate) {
      // Set an error message if due date is missing
      setError("Choose a due date.");
      // Stop further function execution early
      return;
    }

    // Set saving to true to show loading state and disable the submit button
    setSaving(true);

    try {
      // Send the new task data object to our backend API via createTask()
      await createTask({ title, description, priority, dueDate });
      // Set success message confirming the task was successfully saved
      setSuccess("Task saved.");

      // Reset the title state back to an empty string
      setTitle("");
      // Reset the description state back to an empty string
      setDescription("");
      // Reset priority select back to the default "Medium"
      setPriority("Medium");
      // Reset due date state back to an empty string
      setDueDate("");

      // Call the parent's onSaved callback function to trigger a fresh task fetch in App.jsx
      onSaved();
    } catch (err) {
      // Show the error message returned from the backend or network failure
      setError(err.message);
    } finally {
      // Turn off the saving loading state once the request has completed
      setSaving(false);
    }
  }

  // Render the form JSX markup
  return (
    // Form container styled as a card with browser validation disabled in favor of custom validation
    <form className="card" onSubmit={handleSubmit} noValidate>
      {/* Form title heading */}
      <h2>Add a task</h2>

      {/* Label for title input linked via htmlFor for accessibility */}
      <label htmlFor="title">Title</label>
      {/* Controlled input: value is bound to React state, changes update state */}
      <input
        // Connects input to the label's htmlFor="title"
        id="title"
        // Bound directly to the title state variable
        value={title}
        // Updates title state as the user types
        onChange={(e) => setTitle(e.target.value)}
        // Helpful placeholder text providing an example
        placeholder="e.g. Finish chemistry assignment"
      />

      {/* Label for description textarea */}
      <label htmlFor="description">Description</label>
      {/* Controlled textarea for multi-line description text */}
      <textarea
        // Connects textarea to label's htmlFor="description"
        id="description"
        // Initial vertical height of 3 visible text rows
        rows="3"
        // Bound directly to description state variable
        value={description}
        // Updates description state as the user types
        onChange={(e) => setDescription(e.target.value)}
        // Placeholder text guiding user input
        placeholder="Add details about what needs to be done..."
      />

      {/* Row container for side-by-side priority dropdown and date picker */}
      <div className="row">
        {/* Left column holding priority select */}
        <div>
          {/* Label for priority select dropdown */}
          <label htmlFor="priority">Priority</label>
          {/* Controlled select dropdown for choosing task priority */}
          <select
            // Connects select to label's htmlFor="priority"
            id="priority"
            // Bound directly to priority state variable
            value={priority}
            // Updates priority state when the selection changes
            onChange={(e) => setPriority(e.target.value)}
          >
            {/* Low priority option */}
            <option>Low</option>
            {/* Medium priority option (default) */}
            <option>Medium</option>
            {/* High priority option */}
            <option>High</option>
          </select>
        </div>

        {/* Right column holding due date picker */}
        <div>
          {/* Label for due date input */}
          <label htmlFor="dueDate">Due date</label>
          {/* Controlled date picker input */}
          <input
            // Connects date input to label's htmlFor="dueDate"
            id="dueDate"
            // HTML5 date input type rendering native browser date calendar picker
            type="date"
            // Bound directly to dueDate state variable (YYYY-MM-DD format)
            value={dueDate}
            // Updates dueDate state when a date is selected
            onChange={(e) => setDueDate(e.target.value)}
          />
        </div>
      </div>

      {/* Render error banner if there is an error message in state */}
      <Message type="error" text={error} />
      {/* Render success banner if there is a success message in state */}
      <Message type="success" text={success} />

      {/* Submit button: disabled during submission to prevent duplicate clicks */}
      <button type="submit" disabled={saving}>
        {/* Dynamic button text based on whether saving is currently in progress */}
        {saving ? "Saving..." : "Save task"}
      </button>
    </form>
  );
}

// Export TaskForm so it can be imported and rendered in App.jsx
export default TaskForm;
