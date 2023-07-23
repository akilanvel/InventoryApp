import {
    MDBContainer,
    MDBNavbar,
    MDBNavbarBrand,
    MDBNavbarToggler,
    MDBIcon,
    MDBNavbarNav,
    MDBNavbarItem,
    MDBNavbarLink,
    MDBBtn,
    MDBDropdown,
    MDBDropdownToggle,
    MDBDropdownMenu,
    MDBDropdownItem,
    MDBCollapse,
} from 'mdb-react-ui-kit';
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from 'react-router-dom';
import logo from '../../images/logo.jpg';
import axios from 'axios';

function Navbar() {
    const [showBasic, setShowBasic] = useState(false);
    const navigate = useNavigate();
    const [searchQuery, setSearchQuery] = useState("");
    const [orders, setOrders] = useState([]);
    const [errorMsg, setError] = useState("");
    const [username, setUsername] = useState(localStorage.getItem("username"));
    const [token, setToken] = useState(localStorage.getItem("token"));
    useEffect(() => {
        setToken(localStorage.getItem("token"));
        setUsername(localStorage.getItem("username"));

        if (searchQuery !== "") {
            axios
                .get(`http://localhost:8181/product/search/${searchQuery}`, {
                    headers: {
                        Authorization: "Basic " + token,
                    },
                })
                .then((response) => {
                    setOrders(response.data);
                    //localStorage.setItem("orders", orders);
                })
                .catch((err) => {
                    setError("Error fetching orders");
                });
        } else {
            axios
                .get(`http://localhost:8181/product/all`, {
                    headers: {
                        Authorization: "Basic " + token,
                    },
                })
                .then((response) => {
                    setOrders(response.data);
                    //localStorage.setItem("orders", orders);
                })
                .catch((err) => {
                    setError("Error fetching orders");
                });
        }
    }, [searchQuery, token]);

    return (
        <MDBNavbar expand='lg' light bgColor='light'>
            <MDBContainer fluid>
                <MDBNavbarBrand>
                    <img src={logo} alt='Logo' height='40' /> {/* Custom logo image */}
                </MDBNavbarBrand>

                <MDBNavbarBrand  >Customer Dashboard</MDBNavbarBrand>

                <MDBNavbarToggler
                    aria-controls='navbarSupportedContent'
                    aria-expanded='false'
                    aria-label='Toggle navigation'
                    onClick={() => setShowBasic(!showBasic)}
                >
                    <MDBIcon icon='bars' fas />
                </MDBNavbarToggler>

                <MDBCollapse navbar show={showBasic}>
                    <MDBNavbarNav className='mr-auto mb-2 mb-lg-0'>
                        
                    </MDBNavbarNav>



                    <div className='d-flex align-items-center'>
                        <div className='ms-3'>
                            <Link to="/customer/cart">
                                <MDBBtn color='primary' className='form-control-lg'>
                                    Cart
                                </MDBBtn>
                            </Link>
                        </div>
                        <div className='ms-3'>
                            <Link to="/logout">
                                <MDBBtn color='primary' className='form-control-lg'>
                                    Logout
                                </MDBBtn>
                            </Link>
                        </div>
                    </div>

                </MDBCollapse>

            </MDBContainer>
        </MDBNavbar>
    );
}

export default Navbar;