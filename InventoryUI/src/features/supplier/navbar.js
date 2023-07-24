import {
    MDBContainer,
    MDBNavbar,
    MDBNavbarBrand,
    MDBNavbarToggler,
    MDBIcon,
    MDBNavbarNav,
    MDBBtn,
    MDBCollapse,
} from "mdb-react-ui-kit";
import { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../../images/logo.jpg";

function Navbar() {
    const [showBasic, setShowBasic] = useState(false);
    return (
        <MDBNavbar expand="lg" light bgColor="light">
            <MDBContainer fluid>
                <MDBNavbarBrand>
                    <img src={logo} alt='Logo' height='40' /> {/* Custom logo image */}
                </MDBNavbarBrand>

                <MDBNavbarBrand href="#">Supplier Dashboard</MDBNavbarBrand>

                <MDBNavbarToggler
                    aria-controls="navbarSupportedContent"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                    onClick={() => setShowBasic(!showBasic)}
                >
                    <MDBIcon icon="bars" fas />
                </MDBNavbarToggler>

                <MDBCollapse navbar show={showBasic}>
                    <MDBNavbarNav className="mr-auto mb-2 mb-lg-0"></MDBNavbarNav>
                    <form className="d-flex input-group w-auto"></form>
                    &nbsp;&nbsp;&nbsp;
                    <Link to="/logout">
                        <MDBBtn color="primary">Logout</MDBBtn>
                    </Link>
                </MDBCollapse>
            </MDBContainer>
        </MDBNavbar>
    );
}
export default Navbar;