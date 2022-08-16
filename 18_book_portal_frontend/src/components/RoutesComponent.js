import { Routes, Route } from "react-router-dom";
import Login from "../pages/Login";
import Books from "../pages/Books";
import Users from "../pages/Users";
import Book from "../pages/Book";
import Account from "../pages/Account";
import Profile from "../pages/Profile";
import Register from "../pages/Register";
import AdminPanel from "../pages/AdminPanel";
import CreateBook from "../pages/CreateBook";
import Home from "../pages/Home";
import About from "../pages/About";
import UpdateBook from "../pages/UpdateBook";
import UpdateUser from "../pages/UpdateUser";

export default () => {
  return (
    <div className="site-layout-background" style={{ padding: 35, minHeight: 580, marginTop: 40, marginBottom: 50 }}>
      <Routes>
        <Route path="/admin/create" element={<CreateBook />} />

        <Route path="/admin" element={<AdminPanel />} />

        <Route path="/about" element={<About />} />

        <Route path="/books/:id/update" element={<UpdateBook />} />

        <Route path="/books/:id" element={<Book />} />

        <Route path="/books" element={<Books />} />

        <Route path="/users/:id/update" element={<UpdateUser />} />

        <Route path="/users/:id" element={<Profile />} />

        <Route path="/users" element={<Users />} />

        <Route path="/account" element={<Account />} />

        <Route path="/login" element={<Login />} />

        <Route path="/register" element={<Register />} />

        <Route path="/" element={<Home />} />
      </Routes>
    </div>
  );
};
