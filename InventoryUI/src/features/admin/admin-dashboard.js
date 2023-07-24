import Navbar from "./navbar";
import { Button, Container, Typography } from "@mui/material";
import ExecutiveForm from "./components/executive-form";
import { useState } from "react";
import WarehouseForm from "./components/warehouse-form";
import ManagerForm from "./components/manager-form";
import SupplierForm from "./components/supplier-form";

function AdminDashboard() {

    const StartMessage = () => {
        return (
            <Container
                className="rounded-modal-white"
                sx={{
                    backgroundColor: "white",
                    flex: "1",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                }}
            >
                <Typography variant="h5">
                    Click one of the buttons above to begin
                </Typography>
            </Container>
        );
    };

    const [formType, setFormType] = useState();

    return (
        <div className="mb-4">
            <Navbar />
            <Container
                className="rounded-modal-white"
                sx={{
                    display: "flex",
                    justifyContent: "space-around",
                    backgroundColor: "white",
                }}
            >
                <Button
                    variant="contained"
                    onClick={() => {
                        setFormType("EXECUTIVE");
                    }}
                >
                    ADD Executive
                </Button>
                {
                    <Button
                        variant="contained"
                        onClick={() => {
                            setFormType("WAREHOUSE");
                        }}
                    >
                        ADD Warehouse
                    </Button>
                }
                <Button
                    variant="contained"
                    onClick={() => {
                        setFormType("MANAGER");
                    }}
                >
                    ADD Manager
                </Button>
                <Button
                    variant="contained"
                    onClick={() => {
                        setFormType("SUPPLIER");
                    }}
                >
                    ADD Supplier
                </Button>
            </Container>
            {(() => {
                switch (formType) {
                    case "EXECUTIVE":
                        return <ExecutiveForm />;
                    case "WAREHOUSE":
                        return <WarehouseForm />;
                    case "MANAGER":
                        return (
                            <ManagerForm />
                        );
                    case "SUPPLIER":
                        return (
                            <SupplierForm />
                        );
                    default:
                        return <StartMessage />;
                }
            })()}
        </div>
    );
}

export default AdminDashboard;