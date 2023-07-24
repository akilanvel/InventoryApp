import { useState } from "react";
import {
    TextField,
    Button,
    Typography,
    Container,
    Grid,
    Snackbar,
    IconButton,
} from "@mui/material";
import { ArrowBackIosNew } from "@mui/icons-material";
import { Alert } from "@mui/material";
import { useNavigate } from "react-router";
import backgroundImage from '../images/background2.jpg';
import axios from "axios";

function CustomerSignUp() {
    const [name, setName] = useState("");
    const [contact, setContact] = useState("");
    const [age, setAge] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confPassword, setConfPassword] = useState("");
    const [houseNo, setHouseNo] = useState("");
    const [street, setStreet] = useState("");
    const [city, setCity] = useState("");
    const [zipcode, setZipcode] = useState("");
    const [errors, setErrors] = useState({});
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const navigate = useNavigate();

    const handleBack = () => {
        navigate("/");
    };

    const validateInputs = () => {
        let validationErrors = {};

        if (!name.trim()) {
            validationErrors.name = "Name is required.";
        }

        if (!contact.trim()) {
            validationErrors.contact = "Contact is required.";
        }

        if (Number(age) < 18) {
            validationErrors.age = "User must be at least 18.";
        }

        if (!username.trim()) {
            validationErrors.username = "Username is required.";
        }

        if (password.length < 6) {
            validationErrors.password = "Password must be at least 6 characters.";
        }

        if (password !== confPassword) {
            validationErrors.confPassword = "Passwords do not match.";
        }

        setErrors(validationErrors);
        return Object.keys(validationErrors).length === 0;
    };

    const handleSnackbarClose = () => {
        setSnackbarOpen(false);
    };

    const handleSignUp = (event) => {
        // Prevent form submission from refreshing the page
        event.preventDefault();

        const isValid = validateInputs();
        if (isValid) {
            const customerData = {
                name: name,
                contact: contact,
                age: Number(age),
                user: {
                    username: username,
                    password: password,
                },
                address: {
                    house_no: houseNo,
                    street: street,
                    city: city,
                    zipcode: zipcode,
                },
            };

            console.log(customerData);
            setSnackbarOpen(true);
            // Send the customerData to the server here
            axios.post("http://localhost:8181/customer/add", customerData);
        }
        navigate('/');
    };

    return (
        <div>
            <div style={{
                backgroundImage: `url(${backgroundImage})`,
                backgroundSize: 'cover',
                position: 'fixed',
                top: 0,
                left: 0,
                width: '100%',
                height: '100%',
                zIndex: -1
            }} />
            <Container maxWidth="sm" className="rounded-modal-white">
                <div style={{ paddingBottom: "1rem", display: "grid", gridTemplateColumns: "1fr 9fr", placeContent: "center" }}>
                    <IconButton disableRipple>
                        <ArrowBackIosNew onClick={handleBack} />
                    </IconButton>
                    <Typography variant="h4" align="center">
                        Join as a new Customer
                    </Typography>
                </div>
                <Snackbar
                    open={snackbarOpen}
                    autoHideDuration={6000}
                    onClose={handleSnackbarClose}
                >
                    <Alert onClose={handleSnackbarClose} severity="success">
                        Sign Up Successful!
                    </Alert>
                </Snackbar>
                <form onSubmit={handleSignUp} style={{ paddingTop: "2rem" }}>
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
                                label="Age"
                                variant="outlined"
                                fullWidth
                                required
                                type="number"
                                value={age}
                                onChange={(e) => setAge(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="Contact"
                                variant="outlined"
                                fullWidth
                                required
                                value={contact}
                                onChange={(e) => setContact(e.target.value)}
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
                                Sign Up
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Container>
        </div>
    );
}

export default CustomerSignUp;