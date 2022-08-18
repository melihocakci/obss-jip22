import React from "react";
import { Table, Form, Input, Button } from "antd";
import UserService from "../service/UserService";
import { Link } from "react-router-dom";
import clean from "../util/clean";

const columns = [
  {
    title: "Username",
    dataIndex: "username",
    sorter: true,
    width: "20%",
  },
  {
    title: "Read Books",
    dataIndex: "readBooks",
    render: (list) => list.length,
    width: "20%",
  },
  {
    title: "Favorited Books",
    dataIndex: "favoriteBooks",
    render: (list) => list.length,
    width: "20%",
  },
  {
    title: "Profile",
    dataIndex: "id",
    render: (id) => <Link to={"/users/" + id}>See Profile</Link>,
    width: "20%",
  },
];

class UserList extends React.Component {
  constructor(props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
    this.fetch = this.fetch.bind(this);
  }

  state = {
    data: [],
    pagination: {
      current: 1,
      pageSize: 10,
    },
    loading: true,
    username: "",
    sortField: "",
    sortOrder: "",
  };

  componentDidMount() {
    this.fetch();
  }

  handleTableChange = async (pagination, filters, sorter) => {
    await this.setState({
      pagination: pagination,
      sortField: sorter.field,
      sortOrder: sorter.order,
    });
    this.fetch();
  };

  fetch = async () => {
    this.setState({ loading: true });

    const cleanState = clean(this.state);

    const { pagination, sortField, sortOrder, username } = cleanState;

    const { body: users } = await UserService.fetchUsers({
      page: pagination.current - 1,
      size: pagination.pageSize,
      sortField,
      sortOrder,
      username,
    });

    const { body: userCount } = await UserService.fetchUserCount();

    this.setState({
      loading: false,
      data: users,
      pagination: {
        ...this.state.pagination,
        total: userCount, // Mock data
      },
    });
  };

  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  render() {
    const { data, pagination, loading } = this.state;
    return (
      <div>
        <Form
          name="basic"
          labelCol={{
            span: 8,
          }}
          wrapperCol={{
            span: 16,
          }}
          initialValues={{
            remember: true,
          }}>
          <Form.Item
            style={{ margin: "0 auto", width: 400, display: "inline-block" }}
            label="Search User"
            name="username">
            <Input
              onChange={this.handleChange}
              name="username"
              value={this.state.username}
            />
          </Form.Item>
          <Form.Item
            style={{ marginLeft: "12px", width: 400, display: "inline-block" }}>
            <Button onClick={this.fetch}>Search</Button>
          </Form.Item>
        </Form>

        <Table
          columns={columns}
          rowKey={(record) => record.id}
          dataSource={data}
          pagination={pagination}
          loading={loading}
          onChange={this.handleTableChange}
        />
      </div>
    );
  }
}

export default UserList;
