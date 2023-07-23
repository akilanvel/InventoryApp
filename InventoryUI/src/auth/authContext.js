import React, { useEffect, useState } from "react";


export const AuthContext = React.createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        // Update User
    });

    return (
        <AuthContext.Provider value={{ user }}>{children}</AuthContext.Provider>
    )
}