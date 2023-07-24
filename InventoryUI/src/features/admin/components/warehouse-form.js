import { useState } from "react";
import { TextField, Button, Typography, Container, Grid } from "@mui/material";
import { CheckCircleOutline } from "@mui/icons-material";
import axios from "axios";

function WarehouseForm() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confPassword, setConfPassword] = useState("");
    const [name, setName] = useState("");
    const [location, setLocation] = useState("");
    const [errors, setErrors] = useState({});
    const [isSuccessful, setIsSuccessful] = useState(false);

    const validateInputs = () => {
        let validationErrors = {};

        if (!name.trim()) {
            validationErrors.name = "Name is required.";
        }

        if (!location.trim()) {
            validationErrors.contact = "Location is required.";
        }

        if (!username.trim()) {
            validationErrors.username = "Username is required.";
        }

        if (password !== confPassword) {
            validationErrors.confPassword = "Passwords do not match.";
        }

        setErrors(validationErrors);
        return Object.keys(validationErrors).length === 0;
    };

    const handleSignUp = async (event) => {
        // Prevent form submission from refreshing the page
        event.preventDefault();

        const isValid = validateInputs();
        if (isValid) {
            const warehouseData = {
                location,
            };

            // console.log(customerData);
            //   setSnackbarOpen(true);

            // Send the customerData to the server here
            try {
                // Send the customerData to the server
                const response = await axios.post(
                    "http://localhost:8181/warehouse/add",
                    warehouseData
                );

                // Handle the successful response from the server
                // setSnackbarMsg("Executive user created successfully");
                setIsSuccessful(true);
                console.log(response.data);
                // setSnackbarOpen(true);
            } catch (error) {
                // Handle any errors that occur during the API call
                console.log(error.message);
                if (error.response.status === 500) {
                    //   setSnackbarMsg("A user already exists with that username/email");
                } else {
                    //   setSnackbarMsg("Error creating Executive user");
                }
                setIsSuccessful(false);
                // setSnackbarOpen(true);
            }
        } else {
            // setSnackbarMsg("Please fill the required fields correctly");
            //   setSnackbarMsg(Object.values(errors)[0]);
            setIsSuccessful(false);
            //   setSnackbarOpen(true);
        }
    };

    return (
        <div>
            {isSuccessful ? (
                <div>
                    <Container
                        maxWidth="sm"
                        className="rounded-modal-white"
                        style={{ marginTop: "35vh" }}
                    >
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <Typography variant="h4" align="center">
                                    Warehouse successfully added
                                </Typography>
                                <Typography variant="h3">
                                    <CheckCircleOutline color="green" />
                                </Typography>
                            </Grid>
                            {/* <Grid item xs={12} style={{ margin: "0 6rem" }}>
                <Button
                  variant="contained"
                  fullWidth
                  onClick={() => {
                    navigate("/login");
                  }}
                >
                  <CheckCircleOutline />
                  &nbsp; Back to Login
                </Button>
              </Grid> */}
                        </Grid>
                    </Container>
                </div>
            ) : (
                <Container
                    maxWidth="lg"
                    className="rounded-modal-white"
                    sx={{ backgroundColor: "white" }}
                >
                    <form onSubmit={handleSignUp}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <Typography>Basic Information</Typography>
                            </Grid>
                            {/* <Grid item xs={12}>
                <TextField
                  label="Full Name"
                  variant="outlined"
                  fullWidth
                  required
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                />
              </Grid> */}
                            <Grid item xs={12}>
                                <TextField
                                    label="Location"
                                    variant="outlined"
                                    fullWidth
                                    required
                                    value={location}
                                    onChange={(e) => setLocation(e.target.value)}
                                />
                            </Grid>
                            {/* 
              <Grid item xs={12} />
              <Grid item xs={12}>
                <Typography>Account Information</Typography>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  label="Username"
                  variant="outlined"
                  placeholder="example@email.com"
                  required
                  fullWidth
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  label="Password"
                  variant="outlined"
                  fullWidth
                  required
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  label="Confirm Password"
                  variant="outlined"
                  fullWidth
                  required
                  type="password"
                  value={confPassword}
                  onChange={(e) => setConfPassword(e.target.value)}
                />
              </Grid>
              <Grid item xs={12} /> */}
                            <Grid item xs={12}>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    fullWidth
                                    onClick={handleSignUp}
                                >
                                    ADD WAREHOUSE
                                </Button>
                            </Grid>
                        </Grid>
                    </form>
                </Container>
            )}
        </div>
    );
}

export default WarehouseForm;