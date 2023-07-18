import React from "react";
import { useEffect } from "react";
import { Typography } from "@mui/material";
import Button from "react-bootstrap/Button";
import { Link, useNavigate } from "react-router-dom";
import Image from "react-bootstrap/Image";
import "./App.css";
import Navbar from "./NavBar/Navbar";
import Navbar1 from "./NavBar/Navbar";
import Typewriterj from "./Typewriter";
import CommentExampleComment from "./Comment/Comment";

const Home = () => {
  const navigate = useNavigate();

  useEffect(() => {
    let username = localStorage.getItem("username");
    // console.log(username)
    if (username === "" || username === null) {
      navigate("/login");
    }
  }, []);

  return (
    <>
      <Navbar1 />
      <div className="container bg-setter">
        <Typography
          sx={{ margin: "5%" }}
          variant="h3"
          align="center"
          className="font-setter"
        >
          <Image src={require("./download.png")} fluid />
          <br />
          SEARCHABLE WEB PORTAL
        </Typography>
        <Typewriterj />
      </div>
    </>
  );
};

export default Home;
