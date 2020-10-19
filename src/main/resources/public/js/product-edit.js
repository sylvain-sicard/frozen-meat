
const app = new Vue({
  el: '#wrapper',
  
  data() {
    return {
      lists: [],
      product: {}
    };
  },
  mounted () {
  	let uri = window.location.search.substring(1); 
    let params = new URLSearchParams(uri);
    
    if(params.has('code')){
      axios
        .get('/product/code/'+params.get("code"))
        .then(response => {
          this.product = response.data;
        });
    }
     axios
      .get('/list/all')
      .then(response => {
        this.lists = response.data;
      });
  },
  methods: {
    save: function () {
        axios
        .post('/product/addoff', this.product)
        .then(response => {
          this.product = response.data;
        });
  
    },
    saveAndAdd: function (listId) {
      axios
        .post('/product/addoff', this.product)
        .then(response => {
          this.product = response.data;
          axios
            .get('/list/'+listId+'/'+this.product.id+'/1')
            .then(response => {
              
            });
        });
    }
  }
});


