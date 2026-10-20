// Import useEffect for side effects (data fetching) and useState for managing component state
import { useEffect, useState } from "react";
// Import our API service function to fetch all tasks from the server
import { getTasks } from "./services/api.js";
// Import the form component used to create tasks
import TaskForm from "./components/TaskForm.jsx";
// Import the user registration form component
import SignupForm from "./components/SignupForm.jsx";
// Import the list component used to display saved tasks
import TaskList from "./components/TaskList.jsx";

/**
 * App Component (Root Container)
 * The main component that coordinates state and brings together the header, forms, and task list.
 */
function App() {
  // State to store the array of tasks fetched from the database
  const [tasks, setTasks] = useState([]);
  // State to show a loading indicator during the initial fetch from the server
  const [loading, setLoading] = useState(true);
  // State to store any error message if loading tasks fails
  const [error, setError] = useState("");

  // Function to fetch the latest tasks from the backend API and update state
  async function loadTasks() {
    try {
      // Clear any previous error before starting the fetch
      setError("");
      // Call the API service to fetch tasks from GET /api/tasks
      const data = await getTasks();
      // Store the received tasks array in state, which triggers a re-render of TaskList
      setTasks(data);
    } catch (err) {
      // Store the error message in state to display it to the user
      setError(err.message);
    } finally {
      // Turn off the loading indicator once the request finishes (whether success or error)
      setLoading(false);
    }
  }

  // useEffect runs after the component renders on screen
  useEffect(() => {
    // Fetch the initial list of tasks when the app first loads
    loadTasks();
    // An empty dependency array [] means this effect runs exactly once when the component mounts
  }, []);

  // Return the JSX layout for the entire application
  return (
    // React Fragment allows grouping multiple elements without adding an extra div node to the DOM
    <>
      {/* Top banner header for branding and subtitle */}
      <header className="header">
        {/* Main application title */}
        <h1>TaskFlow</h1>
        {/* Brief description of the application */}
        <p>Collect form data in React and save it to MongoDB.</p>
      </header>

      {/* Main layout container with a two-column grid */}
      <main className="layout">
        {/* Left column holding the two input forms */}
        <div className="column">
          {/* Pass loadTasks as a prop callback so TaskForm can refresh the list after saving a new task */}
          <TaskForm onSaved={loadTasks} />
          {/* Render the independent signup form */}
          <SignupForm />
        </div>

        {/* Right column: TaskList receives tasks, loading, and error states as props from App */}
        <TaskList tasks={tasks} loading={loading} error={error} />
      </main>
    </>
  );
}

// Export App so main.jsx can render it as the root of the React app
export default App;
