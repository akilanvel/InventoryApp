import React, { useEffect, useState } from "react";

import axios from "axios";

import { Typography } from "@mui/material";





const SideNav = ({ onSelectItem }) => {

  const [sidebarData, setSidebarData] = useState([]);




  useEffect(() => {

    let token = localStorage.getItem("token");

    axios.get('http://localhost:8181/category/all', {

      headers: {

        'Authorization': 'Basic ' + token

      }

    }).then(response => {

      console.log(response);

      setSidebarData(response.data);

    })

      .catch(error => {

        console.error('Error fetching sidebar data:', error);

      })

    console.log(sidebarData);

  }, []);




  const handleItemClick = (item) => {

    onSelectItem(item);




  }





  return (

    <div style={{ width: "100%", paddingTop: "20px", paddingBottom: "20px" }}>
      <h1>
        Welcome, {localStorage.getItem('username').split("@")[0] + "\n"}
      </h1>
      <div className="sidenav">
        <div
          style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}
        >
          {sidebarData.map((item) => (
            <div key={item.id} style={{ flex: "1" }}>
              <Typography
                onClick={() => handleItemClick(item)}
                style={{
                  padding: "10px",
                  margin: "5px",
                  borderRadius: "20px",
                  cursor: "pointer",
                  background: "#f0f0f0",
                  boxShadow: "0 2px 5px rgba(0, 0, 0, 0.1)",
                  transition: "all 0.3s ease",
                }}
              >
                {item.name}
              </Typography>
            </div>
          ))}
        </div>
      </div>
    </div>

  );

}

export default SideNav;