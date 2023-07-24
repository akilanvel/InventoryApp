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


export default function Orders() {
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [size, setSize] = useState(10);
    const [globalFilter, setGlobalFilter] = useState(null);
    const dt = useRef(null);
    const [orders, setOrders] = useState({});

    const header = (
        <div className="flex flex-wrap gap-2   justify-content-between">
            <h4 className="m-0">Your shopping cart:</h4>

        </div>
    );

    useEffect(() => {
        axios.get("http://localhost:8181/orders")
            .then(response => {
                setOrders(response.data); // Assuming response.data is an array
            })
            .catch(error => {
                console.error("Error fetching orders:", error);
            });
    }, []);

    return (
        <div>
            <Navbar />
            <section className="h-100" style={{ backgroundColor: "#eee" }}>
                <center>
                    <h1>Your orders!</h1>
                </center>

                <br />
                <br />

                <DataTable
                    ref={dt}
                    value={orders}
                    dataKey="id"
                    paginator
                    rows={size}
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products" /** header={header} */
                    globalFilter={globalFilter}
                    header={header}
                >
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
                </DataTable>
            </section>
        </div>
    );
}