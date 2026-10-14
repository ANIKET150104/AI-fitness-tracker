import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import { setCredentials } from "./store/authSlice";
import ActivityForm from "./components/ActivityForm";
import ActivityList from "./components/ActivityList";
import ActivityDetail from "./components/ActivityDetail";

const ActivitiesPage = () => {
  return (
    <Box component="section" sx={{ p: 2, border: "1px dashed grey" }}>
      <ActivityForm onActivityAdded={() => window.location.reload()} />
      <ActivityList />
    </Box>
  );
};

function App() {
  const { token, tokenData, logIn, logOut, isAuthenticated } =
    useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={
            token ? (
              <Navigate to="/activities" replace />
            ) : (
              <Box sx={{ display: "flex", justifyContent: "center", mt: 10 }}>
                <Button
                  variant="contained"
                  sx={{ backgroundColor: "#dc004e" }}
                  onClick={() => logIn()}
                >
                  LOGIN
                </Button>
              </Box>
            )
          }
        />

        <Route
          path="/activities"
          element={token ? <ActivitiesPage /> : <Navigate to="/" replace />}
        />

        <Route
          path="/activities/:id"
          element={token ? <ActivityDetail /> : <Navigate to="/" replace />}
        />

        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Router>
  );
}

export default App;
