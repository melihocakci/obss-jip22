import { BrowserRouter as Router } from "react-router-dom";
import { Layout } from "antd";
import { useState } from "react";
import UserContext from "./context/UserContext";
import "antd/dist/antd.css";
import "./index.css";
import ThisUser from "./util/ThisUser";
import RoutesComponent from "./components/RoutesComponent";
import MenuComponent from "./components/MenuComponent";
import AuthVerify from "./components/AuthVerify";

const { Header, Content, Footer } = Layout;

export default function App() {
  const [user, setUser] = useState(ThisUser.get());

  return (
    <UserContext.Provider value={{ user, setUser }}>
      <Router>
        <AuthVerify />

        <Layout style={{ height: "100vh" }}>
          <Header style={{ position: "fixed", zIndex: 1, width: "100%" }}>
            <img className="logo" src="/logo.png" />
            <MenuComponent />
          </Header>

          <Content
            className="site-layout"
            style={{ padding: "0 50px", marginTop: 64 }}>
            <RoutesComponent />
          </Content>
        </Layout>
      </Router>
    </UserContext.Provider>
  );
}
