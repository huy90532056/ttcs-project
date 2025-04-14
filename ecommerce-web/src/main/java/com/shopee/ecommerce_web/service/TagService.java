package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.TagCreationRequest;
import com.shopee.ecommerce_web.dto.request.TagUpdateRequest;
import com.shopee.ecommerce_web.dto.response.TagResponse;
import com.shopee.ecommerce_web.entity.Tag;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.TagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagService {
    TagRepository tagRepository;

    // Create new Tag
    public TagResponse createTag(TagCreationRequest request) {
        if (tagRepository.existsByTagName(request.getTagName())) {
            throw new AppException(ErrorCode.TAG_EXISTED);
        }

        Tag tag = new Tag();
        tag.setTagName(request.getTagName());

        // Save tag to repository
        tag = tagRepository.save(tag);

        // Convert Tag entity to TagResponse
        TagResponse response = new TagResponse();
        response.setTagId(tag.getTagId());
        response.setTagName(tag.getTagName());

        return response;
    }

    // Get all Tags
    public List<TagResponse> getTags() {
        return tagRepository.findAll().stream()
                .map(tag -> {
                    TagResponse response = new TagResponse();
                    response.setTagId(tag.getTagId());
                    response.setTagName(tag.getTagName());
                    return response;
                })
                .toList();
    }

    // Get Tag by ID
    public TagResponse getTag(String tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED));

        // Convert Tag entity to TagResponse
        TagResponse response = new TagResponse();
        response.setTagId(tag.getTagId());
        response.setTagName(tag.getTagName());

        return response;
    }

    // Update Tag
    public TagResponse updateTag(String tagId, TagUpdateRequest request) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED));

        tag.setTagName(request.getTagName());

        tag = tagRepository.save(tag);

        // Convert updated Tag entity to TagResponse
        TagResponse response = new TagResponse();
        response.setTagId(tag.getTagId());
        response.setTagName(tag.getTagName());

        return response;
    }

    // Delete Tag
    public void deleteTag(String tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new AppException(ErrorCode.TAG_NOT_EXISTED);
        }

        tagRepository.deleteById(tagId);
    }
}
