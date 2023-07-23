import React, { useEffect, useRef, useState } from "react";
import {
    MDBBtn,
    MDBCard,
    MDBCardBody,
    MDBCardImage,
    MDBCol,
    MDBContainer,
    MDBIcon,
    MDBInput,
    MDBRow,
    MDBTypography,
} from "mdb-react-ui-kit";

//import CartCard from "./CartCard";
import axios from "axios";
import Navbar from "./CartNavbar";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";


export default function Cart() {

    const [cart, setCart] = useState([]);
    const [size, setSize] = useState(10);
    const [globalFilter, setGlobalFilter] = useState(null);
    const dt = useRef(null);
    const [cartTotal, setTotal] = useState(0);
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [one, setOne] = useState(1);

    //setCart(JSON.parse(localStorage.getItem("cart")));


    useEffect(() => {
        const cartData = JSON.parse(localStorage.getItem('cart')) || [];
        setCart(cartData);

        calculateTotal(cartData);

        localStorage.setItem('cart', JSON.stringify(cart));
    }, []);

    const calculateTotal = (cartData) => {
        let total = 0;
        cartData.map((p) => {
            total += p.price;
        });
        setTotal(total);
    };

    const getProducts = () => {
        /* Call All APIs to get All Products, Warehouses and Suppliers */
        async function getAllProducts() {
            try {
                const response = await axios.get('http://localhost:8181/product/all');
                //setAllProducts(response.data);
                //setErrMsg("");
            }
            catch (err) {
                //setErrMsg("Network Issue, Something has broken");
            }
        }
        getAllProducts();

    }

    const header = (
        <div className="flex flex-wrap gap-2   justify-content-between">
            <h4 className="m-0">Your shopping cart:</h4>

        </div>
    );

    const actionBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                <button className="btn btn-danger"
                    onClick={() => updateStatusv1(rowData.id)} >REMOVE</button>
                &nbsp;&nbsp;
            </React.Fragment>
        );
    };

    const updateStatusv1 = (productId) => {
        const tempCart = cart;
        const newCart = tempCart.filter(obj => obj.id !== productId);
        setCart(newCart);
        localStorage.removeItem('cart');
        localStorage.setItem('cart', JSON.stringify(newCart));
        calculateTotal(newCart);
    }

    const checkout = () => {
        let customerId = -1;

        axios
            .get(`http://localhost:8181/customer/getId/${localStorage.getItem('username')}`, {
                headers: {
                    Authorization: "Basic " + token,
                },
            })
            .then((response) => {
                customerId = response.data;
                console.log(customerId);

                axios
            .post(`http://localhost:8181/product/customer/purchase/${customerId}`, orders, {
                headers: {
                    Authorization: "Basic " + token,
                },
            })
            .then((response) => {
                console.log("Purchased");
            })
            .catch((error) => {
                console.error("Error fetching customer ID:", error);
            });
            })
            .catch((error) => {
                console.error("Error fetching customer ID:", error);
            });

        const orders = cart.map(p => (
            {
                id: p.id,
                quantity: 1
            }
        ));

        setTotal(0);
        localStorage.removeItem('cart');
        setCart([]);
    }

    return (
        <div>
            <Navbar />
            <section className="h-100" style={{ backgroundColor: "#eee" }}>
                <center>
                    <h1>Welcome to your shopping cart!</h1>
                </center>

                <br />
                <br />

                <DataTable
                    ref={dt}
                    value={cart}
                    dataKey="id"
                    paginator
                    rows={size}
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products" /** header={header} */
                    globalFilter={globalFilter}
                    header={header}
                >
                    <Column
                        field="id"
                        header="Product ID"
                        sortable
                        style={{ minWidth: "8rem" }}
                    ></Column>

                    <Column
                        field="title"
                        header="Product Name"
                        sortable
                        style={{ minWidth: "12rem" }}
                    ></Column>

                    <Column
                        field="price"
                        header="Price"
                        sortable
                        style={{ minWidth: "6rem" }}
                    ></Column>

                    <Column
                        field="category.name"
                        header="Category"
                        sortable
                        style={{ minWidth: "10rem" }}
                    ></Column>

                    <Column
                        body={actionBodyTemplate}
                        exportable={true}
                        style={{ minWidth: "12rem" }}
                    ></Column>
                </DataTable>
            </section>
            <br />
            <h2>Your total: {cartTotal}</h2>
            <button type="button" className="btn btn-success" onClick={checkout}>Checkout</button>
        </div>
    );
}