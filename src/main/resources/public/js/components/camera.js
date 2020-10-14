Vue.use(Vuex);
Vue.component('camera',{
  template:`
  <div class="camera">
    <p>Camera State:{{cameraState}}</p>
	<button v-if="!cameraState" :disabled="isStartEnabled" v-on:click="start">Camera</button>
	<button v-if="cameraState" :disabled="isStartEnabled" v-on:click="stop">Stop</button>
	<button v-if="cameraState" :disabled="isStartEnabled" v-on:click="snapshot">Snapshot</button>
	<button v-if="isPhoto" :disabled="!isPhoto" v-on:click="download">Download</button>
	<div v-if="isPhoto">
		<button v-on:click="upload" v-if="isPhoto">Upload</button>
	</div>
     
	<form class>
    <select v-model="selectedDevice" v-on:change="deviceChange()" size="sm">
      <option v-for="option in options" :value="option.value">{{option.text}}</option>
    </select>
	</form>
     
	<div>
		<video v-show="cameraState" playsinline autoplay></video>
		<canvas v-show="!cameraState"></canvas>
	</div>
  </div>
`,
  data() {
    return {
      video: null,
      canvas: null,
      fileData: null,
      isStartEnabled: true,
      isPhoto: false,
      stream: null,
      currentStream: null,
      devices: [],
      options: [],
      constraints: {},
      selectedDevice: null,
      cameraState: true
    };
  },
  methods: {
    upload: function() {
      
    },
    snapshot: function() {
      this.canvas.width = this.video.videoWidth;
      this.canvas.height = this.video.videoHeight;
      this.canvas
        .getContext("2d")
        .drawImage(this.video, 0, 0, this.canvas.width, this.canvas.height);
      this.fileData = this.canvas.toDataURL("image/jpeg");
      this.isPhoto = true;
      this.cameraState = false;
      //remove any hidden links used for download
      let hiddenLinks = document.querySelectorAll(".hidden_links");
      for (let hiddenLink of hiddenLinks) {
        document.querySelector("body").remove(hiddenLink);
      }
    },
    stop: function() {
      this.video.pause();
      if (this.currentStream) {
        this.currentStream.getTracks().forEach(track => {
          track.stop();
        });
        this.video.srcObject = null;
      }
      this.video.removeAttribute("src");
      this.video.load();
      this.canvas
        .getContext("2d")
        .clearRect(0, 0, this.canvas.width, this.canvas.height);
      this.isPhoto = false;
      this.cameraState = false;
    },
    start: function() {
      this.stop();
      //when starting up again use first option
      this.selectedDevice = this.options[0].value;
      this.getMedia().then(result => {
        this.isStartEnabled = false;
        this.cameraState = true;
        // eslint-disable-next-line no-console
        console.log("start camera:", result);
      });
    },
    download: function() {
      if (this.fileData) {
        this.canvas.width = this.video.videoWidth;
        this.canvas.height = this.video.videoHeight;
        this.canvas
          .getContext("2d")
          .drawImage(this.video, 0, 0, this.canvas.width, this.canvas.height);
        let a = document.createElement("a");
        a.classList.add("hidden-link");
        a.href = this.fileData;
        a.textContent = "";
        a.target = "_blank";
        a.download = "photo.jpeg";
        document.querySelector("body").append(a);
        a.click();
      }
    },
    getMedia: async function() {
      try {
        this.stream = await navigator.mediaDevices.getUserMedia(
          this.constraints
        );
        window.stream = this.stream;
        this.currentStream = window.stream;
        this.video.srcObject = window.stream;
        return true;
      } catch (err) {
        throw err;
      }
    },
    deviceChange: function() {
      this.stop();
      //don't change selected device
      this.setConstraints();
      this.getMedia().then(result => {
        this.isStartEnabled = false;
        this.cameraState = true;
        // eslint-disable-next-line no-console
        console.log("device change:", result);
      });
    },
    setConstraints: function() {
      const videoContstraints = {};
      if (this.selectedDevice === null) {
        videoContstraints.facingMode = "environment";
      } else {
        videoContstraints.deviceId = {
          exact: this.selectedDevice
        };
      }
      this.constraints = {
        video: videoContstraints,
        audio: false
      };
    },
    getDevices: async function() {
      // if (!navigator.mediaDevices || !navigator.mediaDevices.enumerateDevices) {
      //   return false;
      // }
      try {
        await navigator.mediaDevices.getUserMedia({audio: false, video: true});   
        let allDevices = await navigator.mediaDevices.enumerateDevices();   
        for (let mediaDevice of allDevices) {
          console.log('Media device : ');
          console.log(mediaDevice);
          if (mediaDevice.kind === "videoinput") {
            console.log('OK');
            let option = {};
            option.text = mediaDevice.label;
            option.value = mediaDevice.deviceId;
            this.options.push(option);
            this.devices.push(mediaDevice);
          }
        }
        // let allDevices = await navigator.mediaDevices.enumerateDevices();
        // for (let mediaDevice of allDevices) {
        //   console.log('Media device : ');
        //   console.log(mediaDevice);
        //   if (mediaDevice.kind === "videoinput") {
        //     let option = {};
        //     option.text = mediaDevice.label;
        //     option.value = mediaDevice.deviceId;
        //     this.options.push(option);
        //     this.devices.push(mediaDevice);
        //   }
        // }
        return this.devices.length>0;
      } catch (err) {
        throw err;
      }
    }
  },
  mounted() {
    this.canvas = document.querySelector("canvas");
    this.video = document.querySelector("video");
    this.getDevices()
      .then(res => {
        //when first loaded selected device can use 1st option
        this.selectedDevice = this.options[0].value;
        this.setConstraints();
        // eslint-disable-next-line no-console
        console.log("get devices:", res);
      })
      .then(() => {
        this.getMedia().then(res => {
          this.isStartEnabled = false;
          // eslint-disable-next-line no-console
          console.log("get media", res);
        });
      });
  }
});