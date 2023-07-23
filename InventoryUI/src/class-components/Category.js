import { Component } from "react";

class Category extends Component {
    //constructor
    constructor() {
        super();
        this.state = {
            category: [],
            errorMsg: ''
        };
    }
    //componentDidMount method
    componentDidMount() {
        fetch('http://localhost:8181/category/all')
            .then(response => response.json())
            .then(carry => this.setState({
                category: carry,
            }), (error) => {
                console.log(error);
                this.setState({
                    errorMsg: 'Something went Wrong'
                })
            });
    }
    //render method
    render() {
        return (
            <div> {/* parent div */}
                {this.state.errorMsg === '' ? '' :
                    <div className="alert alert-primary" role="alert"> {this.state.errorMsg} </div>}
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Name</th>
                            <th scope="col">Priority</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.category.map((c, index) => {
                            return (
                                <tr key={index}>
                                    <th scope="row">{++index}</th>
                                    <td>{c.name}</td>
                                    <td>{c.priority}</td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default Category;

/* Life Cycle Hooks:  
when the component is loaded, following methods get called in below mentioned sequence

1. constructor   category:[]
2. componentDidMount() category:[c1,c2,c3]
3. render() 
*/