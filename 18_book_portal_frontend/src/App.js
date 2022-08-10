import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import "antd/dist/antd.css";
import "./index.css";
import { Layout, Menu, Breadcrumb } from "antd";
import Login from "./pages/Login";
import Books from "./pages/Books";
import Users from "./pages/Users";
import Account from "./pages/Account";
import Profile from "./pages/Profile";
import LoginOrProfile from "./components/LoginOrProfile";

const { Header, Content, Footer } = Layout;

export default function App() {
    return (
        <Router>
            <Layout style={{ height: "100vh" }}>
                <Header style={{ position: "fixed", zIndex: 1, width: "100%" }}>
                    <div className="logo" />
                    <Menu theme="dark" mode="horizontal" defaultSelectedKeys={["1"]}>
                        <Menu.Item key="1">
                            <Link to="/">Home</Link>
                        </Menu.Item>
                        <Menu.Item key="2">
                            <Link to="/books">Books</Link>
                        </Menu.Item>
                        <Menu.Item key="3">
                            <Link to="/users">Users</Link>
                        </Menu.Item>
                        <Menu.Item key="4">
                            <LoginOrProfile />
                        </Menu.Item>
                        <Menu.Item key="5">
                            <Link to="/about">About</Link>
                        </Menu.Item>
                    </Menu>
                </Header>
                <Content className="site-layout" style={{ padding: "0 50px", marginTop: 64 }}>
                    <Breadcrumb style={{ margin: "16px 0" }}>
                        <Breadcrumb.Item>Home</Breadcrumb.Item>
                        <Breadcrumb.Item>Books</Breadcrumb.Item>
                        <Breadcrumb.Item>Users</Breadcrumb.Item>
                        <Breadcrumb.Item>About</Breadcrumb.Item>
                    </Breadcrumb>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: 380 }}>
                        <Routes>
                            <Route path="/about" element={<About />} />

                            <Route path="/books" element={<Books />} />

                            <Route path="/users/:id" element={<Profile />} />

                            <Route path="/users" element={<Users />} />

                            <Route path="/account" element={<Account />} />

                            <Route path="/login" element={<Login />} />
                        </Routes>
                    </div>
                </Content>
                <Footer style={{ textAlign: "center" }}>Ant Design ©2018 Created by Ant UED</Footer>
            </Layout>
        </Router>
    );
}

function About() {
    return <h2>About</h2>;
}
