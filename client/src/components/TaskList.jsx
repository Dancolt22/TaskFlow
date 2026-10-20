// Import our Message component to display any error banners
import Message from "./Message.jsx";

/**
 * TaskList Component
 * Displays the list of tasks retrieved from MongoDB.
 * Handles loading, error, empty, and populated data states.
 * @param {Array} tasks - Array of task objects from state
 * @param {boolean} loading - Boolean flag indicating if fetch is in progress
 * @param {string} error - Error message string, if fetch failed
 */
function TaskList({ tasks, loading, error }) {
  // Helper function to format ISO date strings into DD/MM/YYYY format in UTC timezone
  function formatDate(value) {
    // Convert to JavaScript Date and format cleanly without timezone offset shifts
    return new Date(value).toLocaleDateString("en-GB", { timeZone: "UTC" });
  }

  // Render the component's JSX layout
  return (
    // Card container for the task list
    <section className="card">
      {/* Section title heading */}
      <h2>Saved tasks</h2>

      {/* If loading is true, display a simple loading message to the user */}
      {loading && <p>Loading tasks...</p>}

      {/* Render the error message banner if an error occurred during fetch */}
      <Message type="error" text={error} />

      {/* Show this empty state text only when loading is done, no error occurred, and tasks array is empty */}
      {!loading && !error && tasks.length === 0 && (
        <p>No tasks yet. Add your first task.</p>
      )}

      {/* Container unordered list for all task items */}
      <ul className="tasks">
        {/* Loop through each task item in the array and render an <li> element */}
        {tasks.map((task) => (
          // React needs a unique 'key' on each list item to track and update elements efficiently
          <li key={task._id}>
            {/* Header row containing the title and priority badge */}
            <div className="task-head">
              {/* Display the task title in bold */}
              <strong>{task.title}</strong>
              {/* Priority badge styled dynamically based on level (low, medium, or high) */}
              <span className={`badge ${task.priority.toLowerCase()}`}>
                {/* Text inside the badge: Low, Medium, or High */}
                {task.priority}
              </span>
            </div>
            {/* Task description text */}
            <p>{task.description}</p>
            {/* Formatted due date display */}
            <small>Due {formatDate(task.dueDate)}</small>
          </li>
        ))}
      </ul>
    </section>
  );
}

// Export TaskList so it can be imported and rendered in App.jsx
export default TaskList;
