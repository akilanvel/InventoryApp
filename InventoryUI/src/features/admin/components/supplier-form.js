import { useState } from "react";
import {
    TextField,
    Button,
    Typography,
    Container,
    Grid,
} from "@mui/material";
import { CheckCircleOutline } from "@mui/icons-material";
import axios from "axios";

function SupplierForm() {
    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confPassword, setConfPassword] = useState("");
    const [houseNo, setHouseNo] = useState("");
    const [street, setStreet] = useState("");
    const [city, setCity] = useState("");
    const [zipcode, setZipcode] = useState("");
    const [errors, setErrors] = useState({});
    const [isSuccessful, setIsSuccessful] = useState(false);

    const validateInputs = () => {
        let validationErrors = {};

        if (!name.trim()) {
            validationErrors.name = "Name is required.";
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
            const supplierData = {
                user: {
                    username,
                    password,
                    role: "SUPPLIER",
                },
                name,
                address: {
                    hno: houseNo,
                    street,
                    city,
                    zipcode,
                },
            };

            // console.log(customerData);
            // setSnackbarOpen(true);

            // Send the customerData to the server here
            try {
                // Send the customerData to the server
                const response = await axios.post(
                    "http://localhost:8181/supplier/sign-up",
                    supplierData
                );

                // Handle the successful response from the server
                // setSnackbarMsg("Executive user created successfully");
                setIsSuccessful(true);
                // console.log(response.data);
                // setSnackbarOpen(true);
            } catch (error) {
                // Handle any errors that occur during the API call
                // console.log(error.message);
                // if (error.response.status === 500) {
                //   setSnackbarMsg("A user already exists with that username/email");
                // } else {
                //   setSnackbarMsg("Error creating Executive user");
                // }
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
                                    Manager user successfully created
                                </Typography>
                                <Typography variant="h3" >
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
                <Container maxWidth="lg" className="rounded-modal-white" sx={{ backgroundColor: "white" }}>
                    <form onSubmit={handleSignUp}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <Typography>Basic Information</Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    label="Full Name"
                                    variant="outlined"
                                    fullWidth
                                    required
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                />
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
                            <Grid item xs={12}></Grid>
                            <Grid item xs={12}>
                                <Typography>Address Information</Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    label="House Number"
                                    variant="outlined"
                                    fullWidth
                                    value={houseNo}
                                    onChange={(e) => setHouseNo(e.target.value)}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    label="Street"
                                    variant="outlined"
                                    fullWidth
                                    value={street}
                                    onChange={(e) => setStreet(e.target.value)}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    label="City"
                                    variant="outlined"
                                    fullWidth
                                    value={city}
                                    onChange={(e) => setCity(e.target.value)}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    label="Zipcode"
                                    variant="outlined"
                                    fullWidth
                                    value={zipcode}
                                    onChange={(e) => setZipcode(e.target.value)}
                                />
                            </Grid>
                            <Grid item xs={12} />
                            <Grid item xs={12} />
                            <Grid item xs={12}>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    fullWidth
                                    onClick={handleSignUp}
                                >
                                    CREATE SUPPLIER USER
                                </Button>
                            </Grid>
                        </Grid>
                    </form>
                </Container>
            )}
        </div>
    );
}

export default SupplierForm;