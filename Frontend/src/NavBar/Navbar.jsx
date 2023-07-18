import React from "react";
import { useNavigate } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { FaMicroblog } from "react-icons/fa";
import "./navbar.css";

function Navbar1() {
  const navigate = useNavigate();

  const redirectHome = () => {
    navigate("/");
  };

  const redirectInsert = () => {
    navigate("/create");
  };

  const redirectLogin = () => {
    var result = window.confirm("Are you sure you want to logout?");
    if (result) {
      localStorage.clear();
      navigate("/login");
    }
  };

  const redirectSearch = () => {
    navigate("/search");
  };

  const redirectUserArticles = () => {
    navigate("/view/userArticles");
  };

  const redirectViewAll = () => {
    navigate("/display");
  };

  return (
    <div className="navbar-width">
      <Navbar bg="dark" data-bs-theme="dark" className="h5">
        <Container>
          <Navbar.Brand href="#" onClick={redirectHome} className="my-2 mx-3">
            BLOGGER&nbsp;
            <FaMicroblog />
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link onClick={redirectSearch}>
                <strong>Search</strong>
              </Nav.Link>

              <Nav.Link onClick={redirectViewAll}>View All Articles</Nav.Link>
              <NavDropdown
                title={localStorage.getItem("username")}
                id="basic-nav-dropdown"
              >
                <NavDropdown.Item onClick={redirectInsert}>
                  INSERT
                </NavDropdown.Item>
                <NavDropdown.Item onClick={redirectUserArticles}>
                  User Articles
                </NavDropdown.Item>
                <NavDropdown.Item onClick={redirectLogin}>
                  LOGOUT
                </NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </div>
  );
}

export default Navbar1;
