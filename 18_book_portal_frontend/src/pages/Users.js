import React from "react";
import { Table, Form, Input, Button, message } from "antd";
import UserService from "../service/UserService";
import { Link } from "react-router-dom";
import clean from "../util/clean";

const columns = [
  {
    title: "Username",
    dataIndex: "username",
    sorter: true,
  },
  {
    title: "Email",
    dataIndex: "email",
    sorter: true,
  },
  {
    title: "Gender",
    dataIndex: "gender",
  },
  {
    title: "Read Books",
    dataIndex: "readBooks",
    render: (list) => list.length,
  },
  {
    title: "Favorited Books",
    dataIndex: "favoriteBooks",
    render: (list) => list.length,
  },
  {
    title: "Profile",
    dataIndex: "id",
    render: (id) => <Link to={"/users/" + id}>See Profile</Link>,
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

    const response = await UserService.fetchUsers({
      page: pagination.current - 1,
      size: pagination.pageSize,
      sortField,
      sortOrder,
      searchParam: username,
    });

    if (!response.success) {
      message.error(response.message);
    }

    this.setState({
      loading: false,
      data: response.body.list,
      pagination: {
        ...this.state.pagination,
        total: response.body.total, // Mock data
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
