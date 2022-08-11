import React from "react";
import "antd/dist/antd.css";
import { Table } from "antd";
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
    state = {
        data: [],
        pagination: {
            current: 1,
            pageSize: 10,
        },
        loading: false,
    };

    componentDidMount() {
        const { pagination } = this.state;
        this.fetch({ pagination });
    }

    handleTableChange = (pagination, filters, sorter) => {
        this.fetch({
            sortField: sorter.field,
            sortOrder: sorter.order,
            pagination,
            ...filters,
        });
    };

    fetch = async (params = {}) => {
        this.setState({ loading: true });

        const data = await BookService.fetchBooks(params);
        const bookCount = await BookService.fetchBookCount();

        console.log(JSON.stringify(data));

        this.setState({
            loading: false,
            data: data,
            pagination: {
                ...params.pagination,
                total: bookCount, // Mock data
            },
        });
    };

    render() {
        const { data, pagination, loading } = this.state;
        return (
            <Table
                columns={columns}
                rowKey={(record) => record.id}
                dataSource={data}
                pagination={pagination}
                loading={loading}
                onChange={this.handleTableChange}
            />
        );
    }
}

export default BookList;
