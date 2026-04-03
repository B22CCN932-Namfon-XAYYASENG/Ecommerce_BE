import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { setToken } from "../../../service/LocalStorageService";
import { Box, CircularProgress, Typography } from "@mui/material";


export default function Authenticate() {
    const navigate = useNavigate();
    const [isLoggedin, setIsLoggedin] = useState(false);
  
    useEffect(() => {
      console.log("Authenticating...");
      console.log(window.location.href);
  
      const accessTokenRegex = /code=([^&]+)/;
      const isMatch = window.location.href.match(accessTokenRegex);
  
      if (isMatch) {
        const authCode = isMatch[1];
        console.log("Auth Code: ", authCode);
  
        // Lấy provider được lưu trước khi redirect sang Google/Facebook
        const provider = sessionStorage.getItem("oauth_provider") || "google";
        const isFacebook = provider === "facebook";
        sessionStorage.removeItem("oauth_provider");
  
        const authUrl = isFacebook
          ? `http://localhost:8081/api/auth/outbound/facebook/authentication?code=${authCode}`
          : `http://localhost:8081/api/auth/outbound/google/authentication?code=${authCode}`;
  
        fetch(authUrl, {
          method: "POST",
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
          })
          .then((data) => {
            console.log("Response Data:", data);
            if (data.content?.token) {
              setToken(data.content.token);
              setIsLoggedin(true);
            } else {
              console.error("No token received:", data);
              navigate("/sign-in");
            }
          })
          .catch((error) => {
            console.error("Authentication error:", error);
            navigate("/sign-in");
          });
      } else {
        // Không có code trong URL → redirect về trang đăng nhập
        navigate("/sign-in");
      }
    }, [navigate]);
  
    useEffect(() => {
      if (isLoggedin) {
        navigate("/");
      }
    }, [isLoggedin, navigate]);
  
    return (
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          gap: "30px",
          justifyContent: "center",
          alignItems: "center",
          height: "100vh",
        }}
      >
        <CircularProgress />
        <Typography>Authenticating...</Typography>
      </Box>
    );
  }
  