import Grid from "@mui/material/Grid";
import React, { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { getActivities } from "../services/api";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import { logout } from "../store/authSlice";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import Button from "@mui/material/Button";

const ActivityList = () => {
  const [activities, setActivities] = useState([]);
  const navigate = useNavigate();
  const { logOut } = useContext(AuthContext);

  const fetchActivities = async () => {
    try {
      const response = await getActivities();
      setActivities(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const dispatch = useDispatch();

  useEffect(() => {
    fetchActivities();
  }, []);

  return (
    <>
      <Grid container spacing={2}>
        {activities.map((activity) => (
          <Grid
            key={activity.id}
            container
            spacing={{ xs: 2, md: 3 }}
            columns={{ xs: 4, sm: 8, md: 12 }}
          >
            <Card
              sx={{ cursor: "pointer" }}
              onClick={() => navigate(`/activities/${activity.id}`)}
            >
              <CardContent>
                <Typography variant="h6">{activity.type}</Typography>
                <Typography>Duration: {activity.duration}</Typography>
                <Typography>Calories: {activity.caloriesBurned}</Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
      <Button
        variant="contained"
        sx={{ backgroundColor: "#dc004e" }}
        onClick={() => {
          console.log("Logout clicked");
          dispatch(logout());
          logOut();
        }}
      >
        LOGOUT
      </Button>
    </>
  );
};

export default ActivityList;
