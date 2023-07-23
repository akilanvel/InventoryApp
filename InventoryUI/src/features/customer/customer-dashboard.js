import { useSearchParams } from "react-router-dom";

import React, { useEffect, useRef, useState } from "react";

import SideNav from "./components/SideNav";

import Navbar from "./navbar";

import { DataTable } from "primereact/datatable";

import { Column } from "primereact/column";

import ProductModal from "./components/ProductModal";

import { Modal } from "bootstrap";

import axios from "axios";

function CustomerDashboard() {
  const [selectedItem, setSelectedItem] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token"));

  const [products, setProducts] = useState([]);

  const [show, setShow] = useState(false);

  const [show1, setShow1] = useState(false);

  const [globalFilter, setGlobalFilter] = useState(null);

  const [size] = useState(10);

  const dt = useRef(null);

  const formatCurrency = (value) => {
    return value.toLocaleString("en-US", {
      style: "currency",
      currency: "USD",
    });
  };

  const priceBodyTemplate = (rowData) => {
    return formatCurrency(rowData.product.price);
  };

  const handleSelectItem = (item) => {
    setSelectedItem(item);

    console.log(item);

    let cid = item.id;

    axios
      .get("http://localhost:8181/product/category/all/" + cid, {
        headers: {
          Authorization: "Basic " + token,
        },
      })
      .then((response) => {
        console.log(response.data);
        setProducts(response.data);
        console.log(products);
        setShow(true);

        //localStorage.setItem('cart', JSON.stringify(response.data)); // FOR TESTING PURPOSES, DELETE LATER
      })
      .catch((error) => {
        console.error("Error fetching sidebar data:", error);
      });

    //console.log(products[0].price);
  };

  useEffect(() => {
    const newData = "New Data";
    setShow1(false);
  }, []);

  let showProducts = (e) => {
    setShow(true);
  };

  return (
    <div>
      <div className="mb-4">
        <div>
          <Navbar />
          
          <SideNav onSelectItem={handleSelectItem} />

          {show && (
            <DataTable
              ref={dt}
              value={products}
              dataKey="id"
              paginator
              rows={size}
              paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
              currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products" /** header={header} */
              globalFilter={globalFilter}
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
                field="totalQuantity"
                header="Quantity"
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
          )}
        </div>
      </div>

      {/* {process()} */}
    </div>
  );
}

export default CustomerDashboard;
