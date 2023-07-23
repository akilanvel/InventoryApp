import React, { useEffect, useState } from "react";

import axios from "axios";

import { Typography } from "@mui/material";

const SideNav = ({ onSelectItem }) => {
  const [sidebarData, setSidebarData] = useState([]);
  const [token, setToken] = useState(localStorage.getItem("token"));

  useEffect(() => {
    let token = localStorage.getItem("token");

    axios
      .get("http://localhost:8181/category/all", {
        headers: {
          Authorization: "Basic " + token,
        },
      })
      .then((response) => {
        console.log(response);

        setSidebarData(response.data);
      })

      .catch((error) => {
        console.error("Error fetching sidebar data:", error);
      });

    console.log(sidebarData);
  }, []);

  const handleItemClick = (item) => {
    onSelectItem(item);
  };

  return (
    <div style={{ width: "25%", paddingTop: "20px" }}>
      <div className="sidenav">
        {sidebarData.map((item) => (
          <Typography key={item.id} onClick={() => handleItemClick(item)}>
            {item.name}
          </Typography>
        ))}
      </div>

      {/* {process()} */}
    </div>
  );
};

export default SideNav;
