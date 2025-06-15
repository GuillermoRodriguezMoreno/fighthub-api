package com.fighthub.fighthubapi.picture;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SupabaseStorageService {

    private final WebClient supabaseBucketWebClient;

    public SupabaseStorageService(WebClient supabaseBucketWebClient) {
        this.supabaseBucketWebClient = supabaseBucketWebClient;
    }

    public Mono<String> upload(MultipartFile file, String folder) {
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String bucketName = "fighthub-pictures";
        String supabaseUrl = "https://srhjieykuiezyiisnvgl.supabase.co";
        return supabaseBucketWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/storage/v1/object/{bucket}/{folder}/{file}")
                        .build(bucketName, folder, filename))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .bodyValue(file.getResource())
                .retrieve()
                .toBodilessEntity()
                .map(response -> {
                    return String.format("%s/storage/v1/object/public/%s/%s/%s",
                            supabaseUrl,
                            bucketName,
                            folder,
                            filename);
                });
    }
}
