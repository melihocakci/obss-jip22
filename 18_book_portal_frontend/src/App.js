import { BrowserRouter as Router } from "react-router-dom";
import { Layout } from "antd";
import { useState } from "react";
import UserContext from "./context/UserContext";
import "antd/dist/antd.css";
import "./index.css";
import ThisUser from "./util/ThisUser";
import RoutesComponent from "./components/RoutesComponent";
import MenuComponent from "./components/MenuComponent";

const { Header, Content, Footer } = Layout;

export default function App() {
  const [user, setUser] = useState(ThisUser.get());

  return (
    <Router>
      <Layout style={{ height: "100vh" }}>
        <Header style={{ position: "fixed", zIndex: 1, width: "100%" }}>
          <img className="logo" src="/logo.png" />

          <UserContext.Provider value={{ user, setUser }}>
            <MenuComponent />
          </UserContext.Provider>
        </Header>

        <Content className="site-layout" style={{ padding: "0 50px", marginTop: 64 }}>
          <UserContext.Provider value={{ user, setUser }}>
            <RoutesComponent />
          </UserContext.Provider>
        </Content>
      </Layout>
    </Router>
  );
}
