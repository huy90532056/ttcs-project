package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.TagCreationRequest;
import com.shopee.ecommerce_web.dto.request.TagUpdateRequest;
import com.shopee.ecommerce_web.dto.response.TagResponse;
import com.shopee.ecommerce_web.service.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {
    TagService tagService;

    @PostMapping
    public ApiResponse<TagResponse> createTag(@RequestBody TagCreationRequest request) {
        TagResponse tagResponse = tagService.createTag(request);
        return ApiResponse.<TagResponse>builder()
                .result(tagResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<TagResponse>> getTags() {
        List<TagResponse> tags = tagService.getTags();
        return ApiResponse.<List<TagResponse>>builder()
                .result(tags)
                .build();
    }

    @GetMapping("/{tagId}")
    public ApiResponse<TagResponse> getTag(@PathVariable String tagId) {
        TagResponse tagResponse = tagService.getTag(tagId);
        return ApiResponse.<TagResponse>builder()
                .result(tagResponse)
                .build();
    }

    @PutMapping("/{tagId}")
    public ApiResponse<TagResponse> updateTag(@PathVariable String tagId, @RequestBody TagUpdateRequest request) {
        TagResponse tagResponse = tagService.updateTag(tagId, request);
        return ApiResponse.<TagResponse>builder()
                .result(tagResponse)
                .build();
    }

    @DeleteMapping("/{tagId}")
    public ApiResponse<String> deleteTag(@PathVariable String tagId) {
        tagService.deleteTag(tagId);
        return ApiResponse.<String>builder()
                .result("Tag has been deleted")
                .build();
    }
}
