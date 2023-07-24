import { useSearchParams } from "react-router-dom";
import React, { useEffect, useRef, useState } from "react";
import SideNav from "./components/SideNav";
import Navbar from "./navbar";
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import ProductModal from "./components/ProductModal";
import { Modal } from "bootstrap";
import axios from "axios";
import Cart from "./components/Cart";
function CustomerDashboard() {

  const [selectedItem, setSelectedItem] = useState(null);
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [list, setList] = React.useState([]);
  const [products, setProducts] = useState([]);
  const [show, setShow] = useState(false);
  const [show1, setShow1] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [globalFilter, setGlobalFilter] = useState(null);
  const [cart, setCart] = useState([]);
  const [size,] = useState(10);
  const dt = useRef(null);

  const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
  };

  const priceBodyTemplate = (rowData) => {
    return formatCurrency(rowData.price);
  }

  const handleSelectItem = (item) => {
    setSelectedItem(item);
    let cid = item.id;
    axios.get('http://localhost:8181/product/category/all/' + cid, {
      headers: {
        'Authorization': 'Basic ' + token
      }
    }).then(response => {
      setProducts(response.data);
      setShow(true);
    })
      .catch(error => {
        console.error('Error fetching sidebar data:', error);
      })
    //console.log(products[0].price);
  };

  const handleSearchQuery = (query) => {
    setSearchQuery(query);
    axios.get('http://localhost:8181/product/search/' + query, {
      headers: {
        'Authorization': 'Basic ' + token
      }
    }).then(response => {
      console.log("Line 94");
      console.log(response.data);
      setProducts(response.data);
    })
      .catch(error => {
        console.error('Error fetching sidebar data:', error);
      })
    //console.log(products[0].price);
  };

  const updateStatusv1 = (product) => {
    let saved = localStorage.getItem('cart'); // using saved could cause error
    if (saved === '') {
      saved = [];
    }
    if (saved !== null) {
      let initialValue = JSON.parse(saved);
      initialValue.push(product);
      //setCart(initialValue);
      const str = JSON.stringify(initialValue);
      localStorage.setItem('cart', str);
    }
    else {
      let cc = [product]
      localStorage.setItem('cart', JSON.stringify(cc));
      //setCart(cc);
      let saved = localStorage.getItem('cart');
    }
  }

  const actionBodyTemplate = (rowData) => {
    return (
      <React.Fragment>
        <button className="btn btn-info"
          onClick={() => updateStatusv1(rowData)} >Add to Cart</button>
      </React.Fragment>
    );
  };

  useEffect(() => {
    const newData = "New Data";
    setShow1(false);
  }, []);
  let showProducts = e => {
    setShow(true);
  };

  const categoryBodyTemplate = (rowData) => {
    return (
      <button
        className="btn btn-primary"
        onClick={() => handleSelectItem(rowData.category)}
      >
        {rowData.category.name}
      </button>
    );
  };

  return (
    <div>
      <div className='mb-4'>
        <div>
          <Navbar onSearchQuery={handleSearchQuery} />
          {/*
                    <Modal show={show} />
                    <button onClick={e => {
                      this.showModal();
                    }}
                    > show Modal </button>
                    */}
          <SideNav onSelectItem={handleSelectItem} />
          {(show || searchQuery !== '') &&
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
                body={priceBodyTemplate}
                header="Price"
                sortable
                style={{ minWidth: "6rem" }}
              ></Column>
              <Column
                field="description"
                header="Description"
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
          }
        </div>
      </div>
      {/* {process()} */}
    </div>
  )
}
export default CustomerDashboard;