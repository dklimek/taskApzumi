import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/models/post.model';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
  post: Post = { 
    userId: 0,
    title: '',
    body: ''
  };
  submitted = false;

  constructor(private postService: PostService) { }

  ngOnInit(): void {
  }

  savePost(): void {
    const data = {
      userId: this.post.userId,
      title: this.post.title,
      body: this.post.body
    };

    this.postService.create(data)
      .subscribe(
        response => {
          console.log(response);
          this.submitted = true;
        },
        error => {
          console.log(error);
        });
  }

  newPost(): void {
    this.submitted = false;
    this.post = {
      userId: 0,
      title: '',
      body: ''
      
    };
  }

}
