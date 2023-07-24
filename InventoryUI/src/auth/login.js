import { TextField, Button, Typography, Container, Snackbar, Alert } from "@mui/material";
import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router";
import backgroundImage from '../images/background10.jpg';

function Login() {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errMsg, setErrMsg] = useState("");
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const navigate = useNavigate();

  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
  };

  const doSignup = () => {
    navigate("/join");
  };

  const doLogin = () => {
    if (username === "admin@incedoinc.com" && password === "admin") {
      navigate("/admin");
      return;
    }

    async function onLogin() {
      try {
        let token = window.btoa(username + ":" + password);
        //console.log(username + ':' + password);
        const response = await axios.get("http://localhost:8181/user/login", {
          headers: {
            Authorization: "Basic " + token,
            "Content-Type": "application/json",
          },
          body: {
            username: username,
            password: password,
          },
        });
        //console.log(response);
        let user = response.data;
        console.log(user);
        /* save token and username in local storage */
        localStorage.setItem("token", token);
        localStorage.setItem("username", username);
        localStorage.setItem("isLoggedIn", "true");
        processRole(user.role);
      } catch (err) {
        setErrMsg("Invalid Credentials");
      }
    }

    if (!(username && password)) {
      setErrMsg("Please enter complete credentials");
      setSnackbarOpen(true);
    } else {
      onLogin();
    }
  };

  const processRole = (role) => {
    switch (role) {
      case "SUPPLIER":
        //go to supplier dashboard : use navigate from react-router
        navigate("/supplier");
        break;
      case "EXECUTIVE":
        //go to executive dashboard : use navigate from react-router
        navigate("/executive");
        break;
      case "CUSTOMER":
        //go to customer dashboard : use navigate from react-router
        navigate("/customer");
        break;
      case "MANAGER":
        navigate("/manager")
      default:
        setErrMsg("UnAuthorized. Contact Admin");
        break;
    }
  }

  return (
    <div>
      <div
        style={{
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: "cover",
          position: "fixed",
          top: 0,
          left: 0,
          width: "100%",
          height: "100%",
          zIndex: -1,
        }}
      />
      <Container maxWidth="sm" className="login-modal">
        <Typography variant="h4" align="center" className="login-title">
          Welcome to Top Shelf
        </Typography>
        <Snackbar
          open={snackbarOpen}
          autoHideDuration={6000}
          onClose={handleSnackbarClose}
        >
          <Alert onClose={handleSnackbarClose} severity="error">
            {errMsg}
          </Alert>
        </Snackbar>
        {localStorage.getItem("isLoggedIn") === "false" && (
          <Typography>Please log in to continue</Typography>
        )}
        <div className="rounded-modal-white">
          <TextField
            label="Username"
            variant="outlined"
            fullWidth
            value={username}
            onChange={(e) => {
              setUsername(e.target.value);
              setErrMsg("");
            }}
            margin="normal"
          />
          <TextField
            label="Password"
            variant="outlined"
            type="password"
            fullWidth
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
              setErrMsg("");
            }}
            margin="normal"
          />
          <Button
            variant="contained"
            color="primary"
            fullWidth
            onClick={doLogin}
            sx={{ mt: 2 }}
          >
            Log In
          </Button>
          <Button
            variant="contained"
            color="primary"
            fullWidth
            onClick={doSignup}
            sx={{ mt: 2 }}
          >
            Sign Up
          </Button>
        </div>
      </Container>
    </div>
  );

}

export default Login;