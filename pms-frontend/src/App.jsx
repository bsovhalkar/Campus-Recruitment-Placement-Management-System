import { ThemeContext } from "./context/ThemeContext";

import { useMemo, useState, useEffect } from "react";

import { ThemeProvider, CssBaseline } from "@mui/material";

import { ToastContainer } from "react-toastify";

import "react-toastify/dist/ReactToastify.css";

import { getTheme } from "./theme/theme";

import AppRoutes from "./routes/AppRoutes";

function App() {
  const [mode, setMode] = useState(localStorage.getItem("theme") || "light");

  useEffect(() => {
    localStorage.setItem("theme", mode);
  }, [mode]);

  const theme = useMemo(() => getTheme(mode), [mode]);

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />

      <ThemeContext.Provider
        value={{
          mode,
          setMode,
        }}
      >
        <AppRoutes />

        <ToastContainer
          position="top-right"
          autoClose={3000}
          hideProgressBar={false}
          newestOnTop
          closeOnClick
          pauseOnHover
          draggable
        />
      </ThemeContext.Provider>
    </ThemeProvider>
  );
}

export default App;
