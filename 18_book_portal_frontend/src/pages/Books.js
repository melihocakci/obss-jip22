import React from "react";
import "antd/dist/antd.css";
import { Table, Form, Button, Input } from "antd";
import BookService from "../service/BookService";
import { Link } from "react-router-dom";

const columns = [
    {
        title: "Name",
        dataIndex: "name",
        sorter: true,
        width: "20%",
    },
    {
        title: "Author",
        dataIndex: "author",
        sorter: true,
        width: "20%",
    },
    {
        title: "Details",
        dataIndex: "id",
        render: (id) => <Link to={"/books/" + id}>See Details</Link>,
        width: "20%",
    },
];

class BookList extends React.Component {
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
        loading: false,
        name: "",
        sortField: "",
        sortOrder: "",
    };

    componentDidMount() {
        this.fetch();
    }

    handleTableChange = async (pagination, filters, sorter) => {
        await this.setState({ pagination: pagination, sortField: sorter.field, sortOrder: sorter.order });
        this.fetch();
    };

    fetch = async () => {
        this.setState({ loading: true });

        const { pagination, sortField, sortOrder, name } = this.state;

        const data = await BookService.fetchBooks({
            page: pagination.current - 1,
            size: pagination.pageSize,
            sortField,
            sortOrder,
            name,
        });

        const bookCount = await BookService.fetchBookCount();

        this.setState({
            loading: false,
            data: data,
            pagination: {
                ...this.state.pagination,
                total: bookCount, // Mock data
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
                        label="Search Book"
                        name="name">
                        <Input onChange={this.handleChange} name="name" value={this.state.name} />
                    </Form.Item>
                    <Form.Item style={{ "margin-left": "12px", width: 400, display: "inline-block" }}>
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

export default BookList;
